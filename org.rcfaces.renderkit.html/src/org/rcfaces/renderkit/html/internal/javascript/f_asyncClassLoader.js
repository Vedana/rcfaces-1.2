/*
 * $Id$
 */
 
/**
 * f_classLoader  Additionnal interactive methods.
 *
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

f_classLoader.prototype._load = function(component, htmlNode, content) {

	f_core.Assert(component && component._kclass, "f_asyncClassLoader._load: component parameter is invalid : "+component);
	f_core.Assert(htmlNode && htmlNode.tagName, "f_asyncClassLoader._load: htmlNode parameter is invalid : "+htmlNode);
	f_core.Assert(typeof(content)=="string", "f_asyncClassLoader._load: component parameter is invalid : "+content);

	var classLoader=this;
	var interactiveMode={
		_component: component,
		_htmlNode: htmlNode,
		_componentPoolLevel: this._componentPool.length,
		
		_loads: new Array,
		_scripts: new Array,
		
		addRequireBundle: function(bundleName) {
			f_core.Debug("f_asyncClassLoader", "Add require bundle '"+bundleName+"'.");

			this._loads.push(bundleName);
			
			return true;
		},
		
		run: function() {
			try {
			 	var bundles=classLoader._bundles;
			 	
			 	var win=f_core.GetWindow(this._component);
			 	
				for(;;) {
					var loads=this._loads;
					
					if (loads.length) {	
						var bundleName=loads.shift();
						
	//					alert("Load bundle "+bundleName);

						if (bundles[bundleName]) {
							continue;
						}
	
						var url=f_env.ComputeJavaScriptURI(bundleName);

						f_core.Debug("f_asyncClassLoader", "Load script '"+bundleName+"' url='"+url+"'.");
							
						var interactive=this;
	
						var doc=this._htmlNode.ownerDocument;
						var script=doc.createElement("script");
						script.type=f_httpRequest.JAVASCRIPT_MIME_TYPE;
						script.src=url;
						script.defer=false;
						script.charset="UTF-8";
						
						if (f_core.IsInternetExplorer()) {
							script.onreadystatechange=function() {
							
								switch(script.readyState) {
								case "loaded":
								case "interactive":
								case "complete":
									break;

								default:
	//								alert("State "+script.readyState); 
									f_core.Debug("f_asyncClassLoader", "Script '"+url+"' state="+script.readyState);
									return;
								}
								
								script.onreadystatechange=null;
	
								f_core.Debug("f_asyncClassLoader", "Script '"+url+"' loaded.");
								
								try {
									interactive.run();
									
								} catch (x) {
									f_core.Error("f_asyncClassLoader", "Run async script throws exception.", x);
								}
							};
					
						} else {
							script.onload=function() {
	//							alert("URL "+url+" loaded !");
								f_core.Debug("f_asyncClassLoader", "Script '"+url+"' loaded.");
	
								script.onload=null; 

								try {
									interactive.run();
									
								} catch (x) {
									f_core.Error("f_asyncClassLoader", "Run async script throws exception.", x);
								}
							};
						}
						
						doc.body.appendChild(script);
						
						return;
					}
					
					var scripts=this._scripts;
					if (scripts.length) {
						var script=scripts.shift();
						
	//					alert("Eval script: "+script);
						
						if (typeof(script)=="object") {
							var scriptElement=document.createElement("script");
							scriptElement.src=script.src;
							
							if (script.type) {
								scriptElement.type=script.type;
							} else {
								scriptElement.type=f_httpRequest.JAVASCRIPT_MIME_TYPE;
							}
							if (script.charset) {
								scriptElement.charset=script.charset;
							}							
							scriptElement.defer=false;
							
							document.body.appendChild(scriptElement);
							
							continue;
						}
						
						script=f_core.Trim(script);
						
						// Pour eviter un anti-virus plutot indelicat !
						if (!script.indexOf("<!--")) {
							var v=script.indexOf("//-->");
							if (v>0) {
								var newScript=script.substring(5, v);
								newScript=f_core.Trim(newScript);

								f_core.Debug("f_asyncClassLoader", "Simplify script '"+script+"' newScript='"+newScript+"'.");
							
								script=newScript;
							}
						}
											
						f_core.Debug("f_asyncClassLoader", "Eval script '"+script+"'.");
						
						if (script.length) {
							try {
								f_core.WindowScopeEval(script);
								
							} catch (x) {
								f_core.Error("f_asyncClassLoader", "Eval throws exception; script='"+script+"'.", x);
							}
						}
						continue;
					}
				
					f_core.Debug("f_asyncClassLoader", "End of scripts evaluation.");
									
	//				alert("Fin !");
					classLoader._interactiveMode=undefined;
					classLoader._postLoad(this);
					return;
				}
			} catch (x) {
				f_core.Error("f_asyncClassLoader", "Async script loading exception.", x);
			}
		}
	};
	this._interactiveMode=interactiveMode;

//	content=content.replace(/^\s+|\s+$/g, "");

	var disableScripts=false;
	if (f_core.IsInternetExplorer()) {
		if (window._camelia_scriptDefer) {
		
			f_core.Debug("f_asyncClassLoader", "Add defer to script tags");
		
			disableScripts=true;
			
			var lowerContent=content.toLowerCase();
			
			var pos=content.length();
			for(;;) {
				pos=lowerContent.lastIndexOf("<script", pos);
				if (pos<0) {
					break;
				}
				
				content=content.substring(0, pos+7)+" defer"+content.substring(pos+7);
				pos--;
			}
		}
	}
		
	f_core.Debug("f_asyncClassLoader", "Set content on component id='"+component.id+"' htmlNode='"+htmlNode+"', htmlNode.tag='"+htmlNode.tagName+"' :\n"+content);
	
	try {
		htmlNode.innerHTML=content;

	} catch (x) {
		f_core.Debug("f_asyncClassLoader", "Exception when setting innerHTML for component id='"+component.id+"' htmlNode='"+htmlNode.tagName+"':\n"+content, x);
	}
	
	var forms=f_core.GetElementsByTagName(htmlNode, "form");	
	if (forms.length) {
		f_core.Debug("f_asyncClassLoader", forms.length+" form(s) detected !");
		
		for(var i=0;i<forms.length;i++) {
			var f=forms[i];
			
			f_core.InitializeForm(f);
		}
	}

	if (!disableScripts) {
		var scripts=f_core.GetElementsByTagName(htmlNode, "script");
		f_core.Debug("f_asyncClassLoader", scripts.length+" script(s) detected !");
		
		var interactiveScripts=interactiveMode._scripts;
		for(var i=0;i<scripts.length;i++) {
			var script=scripts[i];
			var src=script.src;
			var type=script.type;
			
			if (src) {
				var js= { src: src, 
						type: type, 
						charset: script.charset };
	
				f_core.Debug("f_asyncClassLoader", "Add element script: src="+src+" type="+type+" charset="+script.charset);
	
				interactiveScripts.push(js);
				continue;
			}
			
			if (typeof(type)=="string" && type.length && type.toLowerCase().indexOf("text/javascript")<0) {
				f_core.Error("f_asyncClassLoader", "Unknown script type: "+script.type);
				continue;
			}
	
			var js=script.text;
	
			f_core.Debug("f_asyncClassLoader", "Add text script: "+js);
			
			interactiveScripts.push(js);
		}
	}
		
	interactiveMode.run();
}
f_classLoader.prototype._postLoad=function(interactiveMode) {
	
	if (this._documentCompleted) {
		var pool=this._componentPool;

		f_core.Info("f_asyncClassLoader", "Call of method f_documentComplete for "+(pool.length-interactiveMode._componentPoolLevel)+" objects.");

		for (var nb=interactiveMode._componentPoolLevel; nb<pool.length; nb++) {
			var obj = pool[nb];
			
			var documentComplete=obj.f_documentComplete;
			if (!documentComplete) {
				continue;
			}
	
			f_core.Assert(typeof(documentComplete)=="function", "Type of f_documentComplete method of class '"+obj._kclass._name+"' is not a function  ! ("+documentComplete+")");
			
			try {
				documentComplete.call(obj);
				
			} catch (x) {
				f_core.Error("f_asyncClassLoader", "f_documentComplete throws exception for component '"+obj.id+"'.", x);
			}
		}
	}

	f_core.Debug("f_asyncClassLoader", "Call load event for interactiveMode component: "+interactiveMode._component);
	
	// Appel du onload des composants parents !
	interactiveMode._component.f_fireEvent("load");
}
