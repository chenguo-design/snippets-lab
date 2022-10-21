classDiagram
direction BT
class AbstractDetectingUrlHandlerMapping
class AbstractHandlerMapping
class AbstractHandlerMethodMapping~T~
class AbstractUrlHandlerMapping
class BeanNameUrlHandlerMapping
class HandlerMapping {
<<Interface>>

}
class MatchableHandlerMapping {
<<Interface>>

}
class PathPatternMatchableHandlerMapping
class RequestMappingHandlerMapping
class RequestMappingInfoHandlerMapping
class RouterFunctionMapping
class SimpleUrlHandlerMapping

AbstractDetectingUrlHandlerMapping  -->  AbstractUrlHandlerMapping 
AbstractHandlerMapping  ..>  HandlerMapping 
AbstractHandlerMethodMapping~T~  -->  AbstractHandlerMapping 
AbstractUrlHandlerMapping  -->  AbstractHandlerMapping 
AbstractUrlHandlerMapping  ..>  MatchableHandlerMapping 
BeanNameUrlHandlerMapping  -->  AbstractDetectingUrlHandlerMapping 
MatchableHandlerMapping  -->  HandlerMapping 
PathPatternMatchableHandlerMapping  ..>  MatchableHandlerMapping 
PathPatternMatchableHandlerMapping "1" *--> "delegate 1" MatchableHandlerMapping 
RequestMappingHandlerMapping  ..>  MatchableHandlerMapping 
RequestMappingHandlerMapping  -->  RequestMappingInfoHandlerMapping 
RequestMappingInfoHandlerMapping  -->  AbstractHandlerMethodMapping~T~ 
RouterFunctionMapping  -->  AbstractHandlerMapping 
SimpleUrlHandlerMapping  -->  AbstractUrlHandlerMapping 
