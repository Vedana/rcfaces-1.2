/*
 * $Id$
 */


/**
 * This class provides a method to load any images if not loaded yet.
 *
 * @class public final f_imageRepository extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {

	/**
	 * @field private static final number
	 */
	 _IMAGE_OBJECT_MAX_NUMBER: 2,

	/**
	 * @field private static final number
	 */
	 _ASYNC_IMAGE_OBJECT_TIMER: 100,	

	/**
	 * @field private static
	 */
	 _Images: undefined,

	/**
	 * @field private static
	 */
	 _ImagesObjectPool: undefined,

	/**
	 * @field private static number
	 */
	 _ImagesObjectCount: 0,

	/**
	 * @field private static
	 */
	 _ImagesWaiting: undefined,
	 

	/**
	 * Prepare an image. (Preload it if necessary)
	 *
	 * @method public static final
	 * @param String url
	 * @return void
	 */
	PrepareImage: function(url) {
		f_core.Assert(url, "f_imageRepository.PrepareImage: URL must be not NULL !");
		
		if (window._rcfacesPrepareImages===false) {
			return;
		}
		
		var images=f_imageRepository._Images;
		if (!images) {
			images=new Object;
			f_imageRepository._Images=images;
			f_imageRepository._ImagesObjectPool=new Array;
		}

		if (images[url]!==undefined) {
			return;
		}

		var imageObject;
		
		var pool=f_imageRepository._ImagesObjectPool;
		if (pool.length) {
			imageObject=pool.pop();

			f_core.Debug(f_imageRepository, "PrepareImage: Load image '"+url+"'. (Use popped image object '"+imageObject.id+"')");
			
		} else if (f_imageRepository._IMAGE_OBJECT_MAX_NUMBER<0 || 
				f_imageRepository._ImagesObjectCount<f_imageRepository._IMAGE_OBJECT_MAX_NUMBER) {
	
			imageObject=new Image();
			imageObject.id="ImageObject #"+f_imageRepository._ImagesObjectCount;
			imageObject._window=window;
			imageObject.onerror=f_imageRepository._OnErrorHandler;
			imageObject.onload=f_imageRepository._OnLoadHandler;

			f_imageRepository._ImagesObjectCount++;

			f_core.Debug(f_imageRepository, "PrepareImage: Load image '"+url+"'. (Create an image object '"+imageObject.id+"')");

		} else {
			// On la met en attente ...
			
			var waiting=f_imageRepository._ImagesWaiting;
			if (!waiting) {
				waiting=new Array;
				f_imageRepository._ImagesWaiting=waiting;
			}
			
			waiting.push(url);
			
			f_core.Debug(f_imageRepository, "PrepareImage: Load image '"+url+"'. (Queue URL)");			
			return;
		}
			
		images[url]=imageObject;
		
		if (f_imageRepository._ASYNC_IMAGE_OBJECT_TIMER>0) {
			window.setTimeout(function() {
				f_core.Debug(f_imageRepository, "PrepareImage: Async setting of url '"+url+"' to image object '"+imageObject.id+"'.");
	
				imageObject.src=url;		
			}, f_imageRepository._ASYNC_IMAGE_OBJECT_TIMER);
			
		} else {
			f_core.Debug(f_imageRepository, "PrepareImage: Sync setting of url '"+url+"' to image object '"+imageObject.id+"'.");

			imageObject.src=url;		
		}
	},
	/**
	 * @method private static
	 * @return void
	 * @context window:win
	 */
	_OnErrorHandler: function() {
		var win=this._window;

		if (win._rcfacesExiting) {
			return;
		}

		f_core.Error(f_imageRepository, "_OnErrorHandler: Error while loading image '"+this.src+"'.");

//		this.onload=null;
//		this.onerror=null;

		f_imageRepository._NextImage(this, false);
	},
	/**
	 * @method private static
	 * @return void
	 * @context window:win
	 */
	_OnLoadHandler: function() {
		var win=this._window;
		if (win._rcfacesExiting) {
			return;
		}

		f_core.Debug(f_imageRepository, "_OnLoadHandler: Image '"+this.src+"' loaded.");

//		this.onload=null;
//		this.onerror=null;

		f_imageRepository._NextImage(this, true);
	},	
	/**
	 * @method private static
	 * @param Image imageObject
	 * @param boolean status of image loading
	 * @return void
	 */
	_NextImage: function(imageObject, status) {
		var src=imageObject.src;

		if (!f_imageRepository._Images) {
			return; // Nous sommes en cours de desinstallation !
		}
	
		f_imageRepository._Images[src]=status;
		
		var waiting=f_imageRepository._ImagesWaiting;
		if (waiting && waiting.length) {
			var url=waiting.unshift();
			f_core.Debug(f_imageRepository, "_NextImage: Set url '"+url+"' to image object '"+imageObject.id+"'.");

			this.src=url
			return;
		}

		f_core.Debug(f_imageRepository, "_NextImage: Push image object '"+imageObject.id+"' into pool.");
		f_imageRepository._ImagesObjectPool.push(imageObject);
	},
	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer: function() {
		var images=f_imageRepository._Images;
		if (!images) {
			return;
		}		
		f_imageRepository._Images=undefined;
		f_imageRepository._ImagesPool=undefined;
		f_imageRepository._ImagesWaiting=undefined;
		
		for(var url in images) {
			var image=images[url];
			if (!image) {
				continue;
			}
			
//			image.id=undefined;
			image.onload=null;
			image.onerror=null;
			image._window=undefined; // Window
		}
	}
}

new f_class("f_imageRepository", {
	statics: __statics
});
