/*
 * $Id$
 */

/**
 * XML Digester
 * 
 * @class f_xmlDigester extends f_object
 * @author Olivier Oeuillot
 * @version $Revision$
 */
var __static = {
	/**
	 * @field private static final string
	 */
	_MAIN_STACK_NAME: "_--main--_",
	
	/**
	 * @field private static final string
	 */
	_PARAMS_STACK_NAME: "_--params--_",
	
	/**
	 * @field hidden static final number
	 */
	BEGIN_MODE: 0,

	/**
	 * @field hidden static final number
	 */
	BODY_MODE: 1,
	
	/**
	 * @field hidden static final number
	 */
	END_MODE: 2,
	
	/**
	 * @method private static
	 */
	_ObjectCreateRule: function(xmlNode, mode, parameters, body) {
		if (mode!=f_xmlDigester.BEGIN_MODE) {
			return;
		}

       	var realClass = parameters[0];
       	var attClass = parameters[1];       	
        if (attClass) {
  			var attributes=xmlNode.attributes;
  			
  			var att=attributes.getNamedItem(attClass);
  			if (att) {
	           	var value = att.nodeValue;
	            if (value) {
    	            realClass = window[value];
        	    }
			}
        }

		if (!realClass) {
			f_core.Error(f_xmlDigester, "Can not get class to instanciate for rule.");
			return;
		}

		var instance;
		if (realClass.f_getName) {
			instance=realClass.f_newInstance();
			
		} else {
			instance=new Object;
			instance.prototype=realClass.prototype;
		}
        
        digester.f_push(instance);
	},
	
	/**
	 * @method private static
	 */
	_AddSetProperty: function(xmlNode, mode, parameters) {
		if (mode!=f_xmlDigester.BEGIN_MODE) {
			return;
		}

		var name=parameters[0];
		var value=parameters[1];

       	var actualName;
        var actualValue;

		var attributes=xmlNode.attributes;
        for(var i = 0; i < attributes.length; i++) {
        	var attribute=attributes[i];
        	
            var attName = attribute.nodeName;
            var attValue = attribute.nodeValue;
            if (attName==name) {
                actualName = attValue;
                continue;
            }
                
            if (attName==value) {
                actualValue = attValue;
                continue;
            }
        }
		
		var top=digester.f_peek();
		
		digester._setProperty(top, actualName, actualValue);
	},
	
	/**
	 * @method private static
	 */
	_AddSetProperties: function(xmlNode, mode, parameters) {
		if (mode!=f_xmlDigester.BEGIN_MODE) {
			return;
		}
	},
	
	/**
	 * @method private static
	 */
	_AddSetNextRule: function(xmlNode, mode, parameters) {
	},
	
	/**
	 * @method private static
	 */
	_AddSetTopRule: function(xmlNode, mode, parameters) {
	}
}

var __prototype = {
/*
	f_xmlDigester: function() {
		this.f_super(arguments);

	},
*/
	f_finalize: function() {
		this._tree=undefined;
		this._rules=undefined;
		this._root=undefined;
		this._stacks=undefined;
		
		this.f_super(arguments);
	},

	/**
     * This method allows you to access the root object that has been
     * created after parsing.
     * 
	 * @method public
     * @return any the root object that has been created after parsing or null if the digester has not parsed any XML yet.
	 */
	f_getRoot: function() {
		return this._root;
	},
	
	/**
	 * @method public
	 * @param string pattern
	 * @param f_class clazz f_class or a javascript class.
	 * @return void
	 */
	f_addObjectCreate: function(pattern, clazz, attributeName) {
		this._addRule(pattern, f_xmlDigester._ObjectCreateRule, clazz, attributeName);
	},
	
	/**
	 * @method public
	 * @param string pattern 
	 * @param string name
	 * @param string value  (if NULL take name as value !)
	 * @param optional string name2
	 * @param optional string value2
	 * @return void
	 */
	f_addSetProperty: function(pattern, name, value, name2, value2) {
		this._addRule(pattern, f_xmlDigester._AddSetProperty, arguments);	
	},
	
	/**
	 * @method public
	 * @param string pattern 
	 * @return void
	 */
	f_addSetProperties: function(pattern, attributes) {
	
		// Parameters:
		//   "toto", ["titi", "attTiti"], "equ"
	
		this._addRule(pattern, f_xmlDigester._AddSetProperties, clazz);	
	},
	
	/**
	 * @method public
	 * @param string pattern 
	 * @param string name  Name of method to call to append top object of the stack, to the previous top object.
	 * @return void 
	 */
	f_addSetNextRule: function(pattern, name) {
		this._addRule(pattern, f_xmlDigester._AddSetNextRule, name);	
	},
	
	/**
	 * @method public
	 * @param string pattern 
	 * @param string name  Name of method to call to append the second top object of the stack, to the top object.
	 * @return void 
	 */
	f_addSetTopRule: function(pattern, name) {
		this._addRule(pattern, f_xmlDigester._AddSetTopRule, name);	
	},
	
	f_addCallMethod: function(pattern, method, parameters) {
		this._addRule(pattern, f_xmlDigester._AddCallMethod, method, parameters);	
	},
	
	/**
	 * @method private
	 */
	_addRule: function(pattern, method, parameters) {
		var rules=this._rules;
		if (!rules) {
			rules=new Array;
			this._rules=rules;
		}
				
		if (arguments.length>2) {
			parameters=new Array;

			f_core.PushArguments(parameters, arguments, 2);
		}
		
		var rule={
			_pattern: pattern,
			_method: method,
			_parameters: parameters
		};
		
		rules.push(rule);
		
		this._tree=undefined;
		
		return rule;
	},
	
	/**
	 * @method private
	 */
	_computeTree: function() {
		var tree=this._tree;
		if (tree) {
			return true;
		}
		tree=new Object;
		this._tree=tree;
		
		var rules=this._rules;
		if (!rules) {
			return tree;
		}
		
		var cacheRules=new Object;
		
		for(var i=0;i<rules.length;i++) {
			var rule=rules[i];
			var pattern=rule._pattern;
			
			var node=cacheRules[pattern];
			
			if (!node) {
				node=tree;
				
				var ss=pattern.split("/");
				for(var j=0;j<ss.length;j++) {
					var s=ss[j];
					
					var n=node[s];
					if (n) {
						node=n;
						continue;
					}
					n=new Object;
					node[s]=n;
					node=n;
				}
				
				cacheRules[pattern]=node;
			}
			
			var rs=node._rules;
			if (!rs) {
				rs=new Array;
				node._rules=rs;
			}
			
			rs.push(rule);
		}		
		
		return tree;
	},

	/**
	 * @method private
	 */
	_parseNode: function(xmlNode, ruleNode) {
		var tagName=xmlNode.tagName;
		
		var rule=ruleNode[tagName];
		if (rule) {
			this._parseRule(xmlNode, rule);
		}
		
		rule=ruleNode["*"];
		if (rule) {
			this._parseRule(xmlNode, rule);
		}
	},
	
	/**
	 * @method private
	 */
	_parseRule: function(xmlNode, ruleNode) {
		var rules=ruleNode._rules;
		for(var i=0;i<rules.length;i++) {
			var rule=rules[i];
			
			var method=rule._method;
			var parameters=rule._parameters;

			method.call(this, xmlNode, f_xmlDigester.BEGIN_MODE, parameters);
		}
		
		var body="";
		
		var children=xmlNode.childNodes;
		if (children && children.length>0) {
			for(var i=0;i<children.length;i++) {
				var xmlChild=chilren[i];
				
				switch(xmlChild.nodeType) {
				case 1: // Element Node
					this._parseNode(xmlChild, ruleNode);
					break;
					
				case 3: // Text Node
				case 4: // CDATA 
					body+=xmlChild.data;
					break;
				}
				
			}
		}

		for(var i=0;i<rules.length;i++) {
			var rule=rules[i];
			
			var method=rule._method;
			var parameters=rule._parameters;

			method.call(this, xmlNode, f_xmlDigester.BODY_MODE, parameters, body);
		}

		for(var i=rules.length;i>0;) {
			var rule=rules[--i];
			
			var method=rule._method;
			var parameters=rule._parameters;

			method.call(this, xmlNode, f_xmlDigester.END_MODE, parameters);
		}
	},
	
	/**
	 * Pop the top object off of the parameters stack, and return it. If there are no objects on the stack, return null
	 * 
	 * @method public
	 * @return any the top Object on the stack or or null if the stack is either empty or has not been created yet
	 */
	f_popParams: function() {
		return this.f_pop(f_xmlDigester._PARAMS_STACK_NAME);
	},
	
	/**
	 * Pops (gets and removes) the top object from the stack with the given name. <br>
	 * Note: a stack is considered empty if no objects have been pushed onto it yet.
	 * 
	 * @method public
	 * @param optional string name Name of stack
	 * @return any the top Object on the stack or or null if the stack is either empty or has not been created yet
	 */
	f_pop: function(name) {
		var stack=this._getStack(name);
		if (!stack) {
			return null;
		}
		
		return stack.pop();
	},
	/**
	 * Push a new object onto the top of the parameters stack.
	 *
	 * @method public
	 * @param any object The new object
	 * @return void
	 */
	f_pushParams: function(object) {
		this.f_push(f_xmlDigester._PARAMS_STACK_NAME, object);
	},
	
	/**
	 * Push a new object onto the top of the object stack.
	 *
	 * @method public
	 * @param optional string name Name of stack
	 * @param any object The new object
	 * @return void
	 */
	f_push: function(name, object) {
		if (arguments.length==1) {
			object=name;
			name=undefined;
		}
		
		var stacks=this._stacks;
		if (!stacks) {
			stacks=new Object;
			this._stacks=stacks;
		}
		
		if (!name) {
			name=f_xmlDigester._MAIN_STACK_NAME;
		}
		
		var stack=stacks[name];
		if (!stack) {
			stack=new Array;
			stacks[name]=stack;
		}
		
		stack.push(object);
		
		if (stack.length==1 && name==f_xmlDigester._MAIN_STACK_NAME) {
			this._root=object;
		}
	},
	
	/**
	 * Return the n'th object down the parameters stack, where 0 is the top element and [getCount()-1] is the bottom element. If the specified index is out of range, return null.
	 * 
	 * @method public
	 * @param optional number index Index of the desired element, where 0 is the top of the stack, 1 is the next element down, and so on.
	 * @return any 
	 */
	f_peekParams: function(index) {
		return this.f_peek(f_xmlDigester._PARAMS_STACK_NAME, index);
	},
	
	/**
	 * Return the n'th object down the stack, where 0 is the top element and [getCount()-1] is the bottom element. If the specified index is out of range, return null.
	 * 
	 * @method public
	 * @param optional string name Name of stack
	 * @param optional number Index of the desired element, where 0 is the top of the stack, 1 is the next element down, and so on.
	 * @return any
	 */
	f_peek: function(name, index) {
		if (typeof(name)=="number") {
			index=name;
			name=undefined;
		}
		
		var stack=this._getStack(name);
		if (!stack) {
			return null;
		}
		
		if (index===undefined) {
			index=0;
		}
		
		return stack[stack.length-index-1];
	},
	
	/**
	 * @method private
	 */
	_getStack: function(name) {
		if (!name) {
			name=f_xmlDigester._MAIN_STACK_NAME;
		}
		
		var stacks=this._stacks;
		if (!stacks) {
			return null;
		}
		
		return stacks[name];
	},
	
	/**
	 * Is the stack with the given name empty?<br>
	 * Note: a stack is considered empty if no objects have been pushed onto it yet.
	 *
	 * @method public
	 * @param optional string name The name of the stack whose emptiness should be evaluated.
	 * @return boolean <code>true</code> if the given stack if empty 
	 */
	f_isEmpty: function(name) {
		return this.f_getCount(name)==0;
	},
	
    /**
     * Return the current depth of the element stack.
     * 
     * @method public
     * @param  optional string name The name of the stack whose depth should be evaluated.
     * @return number The depth of the stack.
     */
	f_getCount: function(name) {		
		var stack=this._getStack(name);
		if (!stack) {
			return 0;
		}
		
		return stack.length;
	},
	
	/**
	 * Parse the content of the specified URI using this Digester.
     * Returns the root element from the object stack (if any).
     *
	 * @method public
	 * @param Object source XML data to be parsed.
	 * @return any Root object
	 */
	f_parse: function(source) {
		var document=source;
		if (typeof(source)=="string") {
			// convertir en document
			// Si URL, telecharger le document !
			
			document=f_xml.FromString(source);
		}
		
		var tree=this._computeTree();
		
		var stack=new Array;
		var root=document.rootElement;
		var currentPath=root.tagName;
		
		this._parseNode(root, tree);
		
		return this._root;
	},
	
	/**
     * Clear the current contents of the object stack.
     * <p>
     * Calling this method <i>might</i> allow another document of the same type
     * to be correctly parsed. However this method was not intended for this 
     * purpose. In general, a separate Digester object should be created for
     * each document to be parsed.
     *
	 * @method public
	 * @return void
	 */
	f_clear: function() {
		this._stacks=undefined;
	}
}
var f_xmlDigester=new f_class("f_xmlDigester", null, __static, __prototype);
