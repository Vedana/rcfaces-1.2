/*
 * $Id$
 */

/**
 * f_iconDnDInfo class
 *
 * @class public f_iconDnDInfo extends f_abstractElementDnDInfo
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
			
	/**
	 * @field static final String
	 */
	DND_SOURCE_IMAGE: "dnd.source.image",
	
	/**
	 * @field static final Number
	 */
	DND_SOURCE_IMAGE_WIDTH: "dnd.source.imageWidth",
	
	/**
	 * @field static final Number
	 */
	DND_SOURCE_IMAGE_HEIGHT: "dnd.source.imageHeight",
	
	/**
	 * @field static final String
	 */
	_NAME: "icon",
	
	/**
	 * @field static final String
	 */
	_CLASSNAME: "f_iconDnDInfo",
		
	Initializer: function() {
		f_dragAndDropEngine.RegisterDragAndDropPopup(f_iconDnDInfo._NAME, function(dragAndDropEngine) {
			return f_iconDnDInfo.f_newInstance(dragAndDropEngine);
		});
	}
};

var __members = {
				
	f_iconDnDInfo: function(dragAndDropEngine) {
		this.f_super(arguments, dragAndDropEngine);
	},
		
	f_finalize: function() {
			
		this.f_super(arguments);
	},
	/**
	 * @method protected
	 * @return String
	 */
	f_getMainElementType: function() {
		return "img";
	},	
	f_fillElement: function(imgElement) {
		imgElement.style.display="none";
		imgElement.width=32;
		imgElement.height=32;

		this.f_setOffsetPosition(-imgElement.width/2, -imgElement.height/2);
	},
	f_updateTarget: function(types, effect, targetComponent, targetItem, targetItemValue, targetAdditionalInformations) {
		this.f_super(arguments, types, effect, targetComponent, targetItem, targetItemValue, targetAdditionalInformations);
	
		var imgElement=this.f_getCursorElement();

		var engine=this.f_getDragAndDropEngine();
		
		var sourceAdditionalInfos=engine.f_getSourceAdditionnalInformations();
		var sourceItem=engine.f_getSourceItem();
		var sourceItemValue=engine.f_getSourceItemValue();
		var sourceComponent=engine.f_getSourceComponent();
		
		var imageURL=this.f_getItemProperty(f_iconDnDInfo.DND_SOURCE_IMAGE, false, sourceComponent, sourceItem, sourceItemValue, sourceAdditionalInfos);		
		if (!imageURL) {
			imgElement.style.display="none";			
			return;
		}
		if (imgElement.src!=imageURL) {
			imgElement.src=imageURL;
		}
		
		var updatePos=false;
		
		var imageWidth=this.f_getItemProperty(f_iconDnDInfo.DND_SOURCE_IMAGE_WIDTH, false, sourceComponent, sourceItem, sourceItemValue, sourceAdditionalInfos);		
		if (!imageWidth) {
			imageWidth="";
		}
		
		if (imgElement.width!=imageWidth) {
			imgElement.width=parseInt(imageWidth);
			updatePos=true;
		}
		
		var imageHeight=this.f_getItemProperty(f_iconDnDInfo.DND_SOURCE_IMAGE_HEIGHT, false, sourceComponent, sourceItem, sourceItemValue, sourceAdditionalInfos);		
		if (!imageHeight) {
			imageHeight="";
		}
		
		if (imgElement.height!=imageHeight) {
			imgElement.height=parseInt(imageHeight);
			updatePos=true;
		}
		
		if (updatePos) {
			this.f_setOffsetPosition(-imgElement.width/2, -imgElement.height/2);
		}
		
		
		imgElement.style.display="block";			
	},
	f_getCursorClassName: function() {
		return f_iconDnDInfo._CLASSNAME;
	}
};
		
new f_class("f_iconDnDInfo", {
	extend: f_abstractElementDnDInfo,
	statics: __statics,
	members: __members
});