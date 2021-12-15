/**
 * @view All
 * @opt output "All.dot"
 * 
 * Edit this to suit your project's libaries. In this example,
 * the libraries classes are packaged within org.example. Those
 * classes will be displayed in red.
 * @match class edu\.mit\.jwi\..*
 * @opt nodefontcolor "red"
 *
 * Android classes will be displayed in blue, with classnames only:
 * @match class android\..*
 * @opt nodefontcolor "blue"
 * @opt !constructors
 * @opt !attributes
 * @opt !operations
 * @opt !visibility
 * @opt !types
 * @opt !enumerations
 * @opt !enumconstants
 *
 * Standard Java classes will be displayed in blue, with classnames only:
 * @match class javax?\..*
 * @opt nodefontcolor "blue"
 * @opt !constructors
 * @opt !attributes
 * @opt !operations
 * @opt !visibility
 * @opt !types
 * @opt !enumerations
 * @opt !enumconstants
 *
 * @match class java\.lang\..*
 * @opt hide
 */
class All {}