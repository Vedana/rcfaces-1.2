/*
 * $Id$
 */
 
/**
 * Aspect NamingContainer
 *
 * @aspect fa_namingContainer
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
	
	/** 
	 * @field public static string
	 */
	SeparatorChar:		":",

	/** 
	 * @field private static final string
	 */
	_NAMING_CONTAINER_ATTRIBUTE:		"v:nc",
	
	/** 
	 * @field private static final string
	 */
	_NAMING_CONTAINER_COMPONENT:		"v:namingContainer",
	
	/**
	 * @method public static final 
	 * @param HTMLElement component
	 * @param string id
	 * @return HTMLElement
	 */
	FindComponent: function(component, id) {
		f_core.Assert(component && component.tagName, "fa_namingContainer.FindComponent: Bad component parameter ! ("+component+")");
		f_core.Assert(typeof(id)=="string", "Bad id parameter !");

		if (component.id==id) {
			return component;
		}
		
		var cid=fa_namingContainer.ComputeComponentId(component, id);
		f_core.Debug("fa_namingContainer", "Compute component id '"+id+"' returns '"+cid+"'.");

		return f_core.GetElementById(cid, component.ownerDocument);
	},
	/**
	 * @method hidden static final 
	 * @param HTMLElement component
	 * @param string[] args component ids
	 * @return HTMLElement
	 */
	FindComponents: function(component, args) {
		f_core.Assert(component && component.tagName, "fa_namingContainer.FindComponents: Bad component parameter ! ("+component+")");

		for(var i=0;component && i<args.length;i++) {
			var id=args[i];
			f_core.Assert(typeof(id)=="string", "Bad id parameter (parameter #"+(i+1)+") !");
			
			component=fa_namingContainer.FindComponent(component, id);
		}
		
		return component;
	},
	/**
	 * @method hidden static final 
	 * @param HTMLElement component
	 * @param string id
	 * @return string Identifier
	 */
	ComputeComponentId: function(component, id) {
		f_core.Assert(component && component.tagName, "fa_namingContainer.ComputeComponentId: Invalid component parameter ('"+component+"')");
		f_core.Assert(typeof(id)=="string" && id.length>0, "fa_namingContainer.ComputeComponentId: Invalid id parameter ('"+id+"')");

		if (fa_namingContainer._flatIdentifierMode) {
			return id;
		}
		
		var separator=fa_namingContainer.SeparatorChar;
		
        if (id.charAt(0)==separator) {
        	// Ca commence par un ':'  l'ID est donc en absolue
        	// On y va directe !
        	
	        return id.substring(1);
		}

    	// Le chemin est en relatif 
    	// On remplace le dernier segment du composant, par l'ID recherché !
    	
       	var cid=component.id;

       	if (!fa_namingContainer._IsNamingContainer(component)) {
	       	var idx=cid.lastIndexOf(separator);
	       	
	       	if (idx<0) {
	       		// Pas de container ... !
	       		// On recherche donc à la racine !
	       		return id;
	       	}
	       	
	       	// On prend le container précédant !
       		cid=cid.substring(0, idx);
    	}
    	
    	return cid+separator+id;
	},
	/**
	 * @method private static final
	 */
	_IsNamingContainer: function(component) {
		f_core.Assert(component.tagName, "Component is invalid ! ("+component+").");

		var tagName=component.tagName;
		if (tagName.toUpperCase()=="FORM" || tagName==fa_namingContainer._NAMING_CONTAINER_COMPONENT) {
			return true;
		}
		
		return f_core.GetAttribute(component, fa_namingContainer._NAMING_CONTAINER_ATTRIBUTE)!=null;
	},
	
	/**
	 * @method hidden static final
	 */
	AddNamingContainerAttribute: function(component) {
		component.setAttribute(fa_namingContainer._NAMING_CONTAINER_ATTRIBUTE, "true");
	},
	
	/**
	 * @method hidden static final
	 */
	SetSeparator: function(separator) {
		if (separator===false) {
			fa_namingContainer._flatIdentifierMode=true;
			return;
		}
		
		fa_namingContainer.SeparatorChar=separator;
	},
	
	/**
	 * Search into each forms of the document, a component by its identifier. <br>
	 * It does not initialize the found component.
	 *
	 * @method hidden static final
	 * @param Document doc The document.
	 * @param string id Identifier of the searched component.
	 * @return HTMLElement
	 */
	SearchElementById: function(doc, id) {
		if (fa_namingContainer._flatIdentifierMode) {
			return null;
		}

		var separator=fa_namingContainer.SeparatorChar;
		
		// On ne traite pas les id avec séparateurs
		if (id.indexOf(separator)>=0) {
			return null;
		}
		
		// Nous sommes dans la recherche d'un ID sans séparateur !
		// C'est peut etre un composant dans une form !
		// On passe les forms en revu !
		
		var forms = doc.forms;
		for (var i=0;i<forms.length; i++) {
			var fid=forms[i].id+separator+id;
			
			var obj=doc.getElementById(fid);
			if (!obj) {
				continue;
			}
			
			f_core.Debug("f_core", "SearchElementById of direct id '"+id+"' (without scope).");
			return obj;
		}
		
		return null;
	}
}

var fa_namingContainer=new f_aspect("fa_namingContainer", __static);
	
