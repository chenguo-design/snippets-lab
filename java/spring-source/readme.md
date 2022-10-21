# Spring源码解析

## 	web.xml文件要点

* listener标签中配置listener-class为org.springframework.web.context.ContextLoaderListener都做了什么？

  1. ContextLoaderListener继承了ServletContextListener，其会在容器启动时执行contextInitialized方法，在方法中调用其父类ContextLoader的initWebApplicationContext()方法![](D:\dev\idea\snippets-lab\idea\images\image-20221004203339920.png)
  2. 在该方法中调用createWebApplicationContext方法创建容器![](D:\dev\idea\snippets-lab\idea\images\image-20221004203527936.png)
  
     1. 方法内会调用determineContextClass()决定实例化哪个类。![](D:\dev\idea\snippets-lab\idea\images\image-20221004204006385.png)
     2. determineContextClass()方法内部如下，如果web.xml中没有配置contextClass，实例化配置到spring-web包下contextLoader.properties配置的类（默认配置的XmlWebApplicationContext），如果配置了则使用自定义的类。![](D:\dev\idea\snippets-lab\idea\images\image-20221004204208153.png)
  3. 创建完容器后在contextLoader类中接着配置及初始化容器(XmlWebApplicationContext是ConfigurableWebApplicationContext的子类，上面的if判定是true)![](D:\dev\idea\snippets-lab\idea\images\image-20221004205501627.png)

     1. 初始化容器时会拿到web.xml中配置的CONFIG_LOCATION_PARAM(通过配置<context-param>的属性名设置为contextConfigLocation)，将servletContext设置到上下文。（<font color='red'>注意：如果没有配置contextConfigLocation：则会默认调用XmlWebApplicationContext的getDefaultConfigLocation()方法去找，默认是/WEB-INF/applicationContext.xml</font>)

  
     ```java
     protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac, ServletContext sc) {
        if (ObjectUtils.identityToString(wac).equals(wac.getId())) {
           // The application context id is still set to its original default value
           // -> assign a more useful id based on available information
           String idParam = sc.getInitParameter(CONTEXT_ID_PARAM);
           if (idParam != null) {
              wac.setId(idParam);
           }
           else {
              // Generate default id...
              wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX +
                    ObjectUtils.getDisplayString(sc.getContextPath()));
           }
        }
     
        wac.setServletContext(sc);
        拿到web.xml中配置的contextConfigLocation
        String configLocationParam = sc.getInitParameter(CONFIG_LOCATION_PARAM);
        if (configLocationParam != null) {
           wac.setConfigLocation(configLocationParam);
        }
     
        // The wac environment's #initPropertySources will be called in any case when the context
        // is refreshed; do it eagerly here to ensure servlet property sources are in place for
        // use in any post-processing or initialization that occurs below prior to #refresh
        ConfigurableEnvironment env = wac.getEnvironment();
        if (env instanceof ConfigurableWebEnvironment) {
           ((ConfigurableWebEnvironment) env).initPropertySources(sc, null);
        }
        customizeContext(sc, wac);
        调用父类AbstractApplicationContext的refresh初始化容器
        wac.refresh();
     }
     ```
  
  4. 接下来会将初始化后的容器交给servletContext中
  
  5. ```
     public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        if (servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null) {
           throw new IllegalStateException(
                 "Cannot initialize context because there is already a root application context present - " +
                 "check whether you have multiple ContextLoader* definitions in your web.xml!");
        }
     
        servletContext.log("Initializing Spring root WebApplicationContext");
        Log logger = LogFactory.getLog(ContextLoader.class);
        if (logger.isInfoEnabled()) {
           logger.info("Root WebApplicationContext: initialization started");
        }
        long startTime = System.currentTimeMillis();
     
        try {
           // Store context in local instance variable, to guarantee that
           // it is available on ServletContext shutdown.
           if (this.context == null) {
              this.context = createWebApplicationContext(servletContext);
           }
           if (this.context instanceof ConfigurableWebApplicationContext) {
              ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) this.context;
              if (!cwac.isActive()) {
                 // The context has not yet been refreshed -> provide services such as
                 // setting the parent context, setting the application context id, etc
                 if (cwac.getParent() == null) {
                    // The context instance was injected without an explicit parent ->
                    // determine parent for root web application context, if any.
                    ApplicationContext parent = loadParentContext(servletContext);
                    cwac.setParent(parent);
                 }
                 configureAndRefreshWebApplicationContext(cwac, servletContext);
              }
           }
     	这里会将初始化后的容器交给servletContext      servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
     
           ClassLoader ccl = Thread.currentThread().getContextClassLoader();
           if (ccl == ContextLoader.class.getClassLoader()) {
              currentContext = this.context;
           }
           else if (ccl != null) {
              currentContextPerThread.put(ccl, this.context);
           }
     
           if (logger.isInfoEnabled()) {
              long elapsedTime = System.currentTimeMillis() - startTime;
              logger.info("Root WebApplicationContext initialized in " + elapsedTime + " ms");
           }
     
           return this.context;
        }
        catch (RuntimeException | Error ex) {
           logger.error("Context initialization failed", ex);
           servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, ex);
           throw ex;
        }
     ```

## DispatcherServlet如何拿到全局ServletContext所持有的ApplicationContext?

通过以下链路Servlet.init(ServletConfig)->GenericServlet.init(ServletConfig)->GenericServlet.init()->HttpServletBean.init()->FrameworkServlet.initServletBean()->FrameworkServlet.initWebApplicationContext()最后拿到上面设置的交给servletContext的上下文

## url-pattern填/和/*的区别

/*可以匹配所有请求，包括带后缀的；对于/，可以匹配所有不带后缀以及后缀为.js，.css，.png等静态资源的访问(<font color='red'>注意/不会匹配jsp后缀</font>)

## 获取ApplicationContext的几种方式

```
public class ApplicationContextAcquirer implements ApplicationContextAware {
    /**
     * 1.直接获取
     */
    @Resource
    private ApplicationContext applicationContext;


    @Resource
    private ApplicationContext applicationContext2;
    /**
     * 第二种 实现ApplicationContextAware接口
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext2=applicationContext;
    }

    private ApplicationContext applicationContext3;
    public ApplicationContextAcquirer(ApplicationContext applicationContext){
        this.applicationContext3=applicationContext;
    }

    /**
     * 第四种
     *
     *
     * applicationContext = SpringApplication.run(Main.class,args);\
     */

    /**
     * 第五种
     */
    //public ApplicationContext getApplicationContext(){
    //    WebApplicationContextUtils.getWebApplicationContext();
    //}
}
```

## HandlerMapping类图

常用的为RequestMappingHandlerMapping（在springmvc中默认没有注册RequestMappingHandlerMapping,需要通过mvc:annotation-driven添加）、SimpleUrlHandlerMapping(如果url-pattern填写的是/或者/*，那么可以配置default-servlet-handler将对静态资源的请求，交给web默认的servlet处理)

![](D:\dev\idea\snippets-lab\java\spring-source\images\HandlerMapping.png)



## 自定义XSD文件

前言：在spring的配置文件头部，经常出现xmlns:namespace-prefix="namespaceURI"，这样带有相同前缀的子元素都会与同一个命名空间相关联，前缀可以看作是命名空间的一个别名。xmlns是默认命名空间，如果不加前缀即为默认命名空间。我们经常看见的XSD文件都是使用XML语言。targetNamespace表示xsd文件中定义的标签所对应的命名空间，实际使用时需要xmlns:xx来导入这个uri

schemaLocation后面的URI是成对出现的，第一个是定义的XML NameSpace值，第二个是Schema文档的地址，Schema处理器将从这个位置读取Schema文档

1. 创建POJO

   ```
   public class Student {
       private String name;
   
       private int age;
   
       public int getAge() {
           return age;
       }
   
       public void setAge(int age) {
           this.age = age;
       }
   
       public String getName() {
           return name;
       }
   
   
   
       public void setName(String name) {
           this.name = name;
       }
   
       public Student(String name) {
           this.name = name;
       }
   
       public Student(String name, int age) {
           this.name = name;
           this.age = age;
       }
   
       @Override
       public String toString() {
           return "Student{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   '}';
       }
   }
   ```

2. 在ClassPath路径下编写xsd文件，命名为spring-student文件夹下。

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <xsd:schema  xmlns="http://www.trg.com/schema/student"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                targetNamespace="http://www.trg.com/schema/student"
                elementFormDefault="qualified">
       <xsd:element name="student" type="student"/>
   
       <xsd:complexType name="student">
           <xsd:attribute name="id" use="optional" type="xsd:string"/>
           <xsd:attribute name="name" use="optional" type="xsd:string"/>
           <xsd:attribute name="age" use="optional" type="xsd:int"/>
       </xsd:complexType>
   </xsd:schema>
   ```

3. 创建对应的BeanDefinitionParser类，用于容器注册对应的BeanDefinition。

   ```java
   package com.cg.dev.xml;
   
   import org.springframework.beans.factory.support.BeanDefinitionBuilder;
   import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
   import org.springframework.util.StringUtils;
   import org.w3c.dom.Element;
   
   public class StudentBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser{
       private static final String STUDENT_NAME_ATTRIBUTE="name";
   
       private static final String STUDENT_AGE_ATTRIBUTE="age";
   
       @Override
       protected void doParse(Element element, BeanDefinitionBuilder builder) {
           String name = element.getAttribute(STUDENT_NAME_ATTRIBUTE);
           String age = element.getAttribute(STUDENT_AGE_ATTRIBUTE);
           if(StringUtils.hasText(name)){
               builder.addPropertyValue(STUDENT_NAME_ATTRIBUTE, name);
           }
           if(StringUtils.hasText(age)){
               builder.addPropertyValue(STUDENT_AGE_ATTRIBUTE,Integer.valueOf(age));
           }
       }
   
       @Override
       protected Class<?> getBeanClass(Element element) {
           return Student.class;
       }
   }
   ```

4. 创建命名空间handler类，用于将刚才创建的BeanDefinitionParser类注入到spring容器。

   ```
   package com.cg.dev.xml;
   
   import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
   
   public class StudentHandler extends NamespaceHandlerSupport {
       @Override
       public void init() {
           System.out.println("注册parser到命名空间handler");
           registerBeanDefinitionParser("student", new StudentBeanDefinitionParser());
       }
   }
   ```

5. 在classpath下（<font color='red'>resource目录，不是webapp目录</font>)创建META-INF文件夹，并放入spring.handlers文件和spring.schemas文件。

   ​	spring.handlers:(<font color='red'>左侧键值对应applicationContext.xml引入的命名空间</font>)

   ```
   http\://www.trg.com/schema/student=com.cg.dev.context.xml.StudentHandler
   ```

   ​	spring.schemas:(<font color='red'>左侧键值对应后面applicationContext.xml引入的xsi:schemaLocation指定的xsd文件地址</font>)

   ```
   http\://www.trg.com/schema/student.xsd=spring-student1.xsd
   ```

6. 建立applicationContext.xml文件。

7. ```
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:
          xmlns:customer="http://www.trg.com/schema/student"
          xsi:schemaLocation="
          http://www.trg.com/schema/student
          http://www.trg.com/schema/student.xsd">
   
       <customer:student id="student3" name="chenguo3" age="2"/>
   </beans>
   ```

## 	default-autowire-candidates、autowire-candidate、default-autowire以及autowire用法及区别

1. default-autowire-candidates和default-autowire用在beans标签内，作用于注入者；autowire-candidate和autowire用在bean标签内，作用于被注入者。（<font color='red'>注入者相当于set方法中方法需要传入的对象，被注入者相当于set方法所在的对象</font>）

2. default-autowire-candidates可以使用通配符

3. 代码示例。

   ```
   <beans default-autowire-candidates="injector" default-autowire="byName">
       <bean id="injector"  class="com.cg.dev.context.inject.Injector"/>
       <bean id="injected" class="com.cg.dev.context.inject.Injected"/>
   </beans>
   ```

## spring基础

1. 形如${A:B}代表什么意思？

   答：表示如果能找到属性A，则用属性A的值，否则使用B，即${key:default_value}

​	2.classpath:和classpath*:有什么区别？

​		答：classpath是指编译后的target中的classes目录，主要包括sources文件夹和resources文件夹，classpath*不仅包含classpath,而且还可将其他的依赖视为classpath。并且通配符可以匹配到多个类路径，则只能使用classpath**.

​	3.项目中需要依赖jar中的资源文件，使用了classpath*但还是访问不到，原因？

​		答：project structure->将要引用模块的compile output加入到web-inf下的classes文件下.

​				方法二：pom中加入插件，<font color='red'>此种情况执行前需要先maven compile,lib文件夹会自动创建</font>，这样lib中的文件夹将会作为source编译到artifact中去。

​	4.idea没有iml文件

​		答：输入mvn idea:module

​	5.版本管理与版本控制区别？

​		答：版本管理是依赖的版本，即我们常用的maven即属于版本管理，其通过artifactId、groupid这种坐标的形式管理依赖的版本。版本控制即vcs（version control system)，比如常见的git、svn。

```java
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <executions>
        <execution>
            <id>install</id>
            <phase>install</phase>
            <goals>
                <goal>sources</goal>
            </goals>
        </execution>
        <execution>
            <id>compile</id>
            <phase>compile</phase>
            <goals>
                <goal>copy-dependencies</goal>
            </goals>
            <configuration>
                <outputDirectory>src/main/webapp/WEB-INF/lib</outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
```

**总结**：最后一定要看project structure对应的artifact war exploded的目录结构，看是否有所需要的依赖，如果没有，1.在modules的依赖中加入要依赖的模块或jar，步骤2：在右边的available element中加入对应的模块编译结果或者jar

## properties文件如何注入到容器中的？



```` java
<context:property-placeholder 
    location="属性文件，多个之间逗号分隔"  
    file-encoding="文件编码"  
    ignore-resource-not-found="是否忽略找不到的属性文件"  
    ignore-unresolvable="是否忽略解析不到的属性，如果不忽略，找不到将抛出异常"  
    properties-ref="本地Properties配置"  
    local-override="是否本地覆盖模式，即如果true，那么properties-ref的属性将覆盖location加载的属性，否则相反"  
    system-properties-mode="系统属性模式，默认ENVIRONMENT（表示先找ENVIRONMENT，再找properties-ref/location的），NEVER：表示永远不用ENVIRONMENT的，OVERRIDE类似于ENVIRONMENT"  
    order="顺序"  />
````

首先进入spring-context.xsd所在的jar包，进入META-INF下的spring.handlers文件，进入第一个ContextNamespaceHandler。

找到其init中的property-placeholder这个parser对应的注册的实体PropertyPlaceholderBeanDefinitionParser

进入AbstractBeanDefinitionParser 类的parse(Element e,ParserContext p)方法

进入AbstractSingleBeanDefinitionParser类的parseInternal(e,p)方法

进入PropertyPlaceholderBeanDefinitionParser类的getBeanClass(element)

以上注册了BeanDefinition，下面分析何时将占位符替换为实际值

PropertiesPropertySource {name='localProperties'}

过程：

AbstractApplicationContext的refresh()

AbstractApplicationContext#invokeBeanFactoryPostProcessors

PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors

PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(registryProcessors, beanFactory)

PropertySourcesPlaceholderConfigurer#postProcessBeanFactory

PropertySourcesPlaceholderConfigurer#processProperties()

PlaceholderConfigurerSupport#doProcessProperties	这里面会遍历所有的beandefinition

```java
//beanDefinition的修改者
BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);
 
//获取所有的beanNames
String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
for (String curName : beanNames) {
	// Check that we're not parsing our own bean definition,
	// to avoid failing on unresolvable placeholders in properties file locations.
	if (!(curName.equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
		//获取BeanDefinition对象
		BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(curName);
		try {
			//修改BeanDefinition中的MutablePropertyValues中的每一个属性值，把属性值有${enjoy.name}修改成真正的参数值，
			visitor.visitBeanDefinition(bd);
		}
		catch (Exception ex) {
			throw new BeanDefinitionStoreException(bd.getResourceDescription(), curName, ex.getMessage(), ex);
		}
	}
}
 
// New in Spring 2.5: resolve placeholders in alias target names and aliases as well.
beanFactoryToProcess.resolveAliases(valueResolver);
//把内嵌的Value解析器设置到BeanFactory中..为@Value的依赖注入做准备
// New in Spring 3.0: resolve placeholders in embedded values such as annotation attributes.
beanFactoryToProcess.addEmbeddedValueResolver(valueResolver);
```
}

BeanDefinitionVisitor#visitBeanDefinition						将bean中注入的属性赋值

````java
public void visitBeanDefinition(BeanDefinition beanDefinition) {
		visitParentName(beanDefinition);
		visitBeanClassName(beanDefinition);
		visitFactoryBeanName(beanDefinition);
		visitFactoryMethodName(beanDefinition);
		visitScope(beanDefinition);
		//如果BeanDefinition存在属性值，则把占位符替换成真正的属性值
		if (beanDefinition.hasPropertyValues()) {
			visitPropertyValues(beanDefinition.getPropertyValues());//点击
		}
		//构造函数占位符
		if (beanDefinition.hasConstructorArgumentValues()) {
			ConstructorArgumentValues cas = beanDefinition.getConstructorArgumentValues();
			visitIndexedArgumentValues(cas.getIndexedArgumentValues());
			visitGenericArgumentValues(cas.getGenericArgumentValues());
		}
	}
````

可以发现此时实体类Student的name属性还是占位符${username1}

![image-20221011145455943](D:\dev\idea\snippets-lab\idea\images\image-20221011145455943.png)

BeanDefinitionVisitor#visitPropertyValues

BeanDefinitionVisitor#resolveValue,将会进入以下分支

```
else if (value instanceof TypedStringValue) 
```

BeanDefinitionVisitor#resolveStringValue StringValueResolver是一个函数式接口

PropertyPlaceholderConfigurer.PlaceholderResolvingStringValueResolver#resolveStringValue

PropertyPlaceholderHelper#replacePlaceholders

PropertyPlaceholderHelper#parseStringValue



<font clolor='red'>如果要查看当前容器中有哪些properties可以断点查看propertiesourcePropertyResolver的改变</font>

何时将propertiesSource注入到容器中呢？

过程：

PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors

​		->invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory)

PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessor

PropertySourcesPlaceholderConfigurer#postProcessBeanFactory

​		->processProperties(beanFactory, new PropertySourcesPropertyResolver(this.propertySources));



核心代码：

```
PropertySource<?> localPropertySource =
      new PropertiesPropertySource(LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME, mergeProperties());
if (this.localOverride) {
   this.propertySources.addFirst(localPropertySource);
}
else {
   this.propertySources.addLast(localPropertySource);
}
```

## spring何时@value及@Resource等属性注入bean

AutowiredAnnotationBeanPostProcessor中的postProcessProperties()方法,方法栈为：

HttpServletBean.init()

FrameworkServlet.initServletBean()

FrameworkServlet.initWebApplicationContext()

FrameworkServlet.createWebApplicationContext()

FrameworkServlet.createWebApplicationContext()

FrameworkServlet.configureAndRefreshWebApplicationContext()

AbstractApplicationContext.refresh()

AbstractApplicationContext.finishBeanFactoryInitialization()

DefaultListableBeanFactory.preInstantiateSingletons()

AbstractBeanFactory.getBean()

AbstractBeanFactory.doGentBean()

DefaultSingletonBeanRegistry.getSingleton()

AbstractBeanFactory.lambda$doGetBean$0

AbstractAutowireCapableBeanFactory.createBean()

AbstractAutowireCapableBeanFactory.doCreateBean()

AbstractAutowireCapableBeanFactory.populateBean()

AutowiredAnnotationBeanPostProcessor.postProcessProperties()

## springMVC要不要引入servlet-api依赖？

只要不使用servlet-api中需要用到的功能，如HttpServletRequest、HttpSession等类，就不需要引入该依赖，否则应该引入，不过引入时注意设置依赖的scope为provided，因为tomcat中已经包含该依赖的字节码文件，如果不设置scope，默认就是compile，会将其打包进artifact(lib)，就会发生冲突。

## SpringBoot为什么不需要再安装tomcat？

因为SprintBoot的spring-boot-starter-web依赖内部引入了spring-boot-starter-tomcat

## SpringBoot的配置文件加载顺序？

application.yml或application.properties的加载顺序：

```` 
File: ./config/  ->  File: ./ 		-> classpath:/config/		->classpath:/
````

application.yml与bootstrap.yml相关：

````java
bootstrap.yml->bootstrap.properties->application.yml->application.properties
1.properties文件先加载，yml文件后加载，因此properties会被yml文件覆盖。
2.bootstrap.yml由父spring applicationContet加载。父applicationcontext被加载到使用application.yml之前（springboot中有两种上下文，一种是bootstrap,另一种是application)
3.application.yml一般用来定义单个应用级别的，如果搭配spring-cloud-config使用,application.yml里面的文件可以实现动态替换。
4.使用spring cloud config server时，应在bootstrap.yml中指定spring.application.name
````

注意：一旦bootstrap.yml被加载，则内容不会被覆盖。bootstrap主要用于从额外的资源来加载配置信息，还可以在本地外部配置文件中解密属性。

## Spring Security相关

1. 默认用户名、密码？

   答：默认用户名：user，默认密码：控制台打印

​	2.spring security的两种资源放行策略？

​		第一种：

````java
@Override
public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");
}

````

​	第二种：

````
configure(HttpSecurity http)
http.authorizeRequests()
        .antMatchers("/hello").permitAll()
        .anyRequest().authenticated()

````

3.如何才能使用encrypt/status接口？

​	答：这是spring-config的功能需要，引入spring-cloud-config包，启动类加上@EnableConfigServer注解

4.The encryption algorithm is not strong enough

​	答：a)检查是否配置了encrypt.key b)是否是配置到bootstrap中

