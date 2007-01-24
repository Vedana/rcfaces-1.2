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

var __static = {

	/**
	 * @field private static
	 */
	 _Images: undefined,

	/**
	 * @field private static
	 */
	 _ImagesPool: undefined,
	 

	/**
	 * Prepare an image. (Preload it if necessary)
	 *
	 * @method public static final
	 * @param String url
	 * @return void
	 */
	PrepareImage: function(url) {
		f_core.Assert(url, "URL must be not NULL !");
		
		var images=f_imageRepository._Images;
		if (!images) {
			images=new Object;
			f_imageRepository._Images=images;
			f_imageRepository._ImagesPool=new Array;
		}

		if (images[url]!==undefined) {
			return;
		}

		f_core.Debug("f_imageRepository", "Load image '"+url+"'.");
			
		var pool=f_imageRepository._ImagesPool;
		var image;
		if (pool.length>0) {
			image=pool.pop();
			
		} else {
			image=new Image();
		}
		
		image.onerror=f_imageRepository._OnErrorHandler;
		image.onload=f_imageRepository._OnLoadHandler;
		image.src=url;		

		images[url]=image;
	},
	/**
	 * @method private static
	 */
	_OnErrorHandler: function() {
		f_core.Error(f_imageRepository, "Error while loading image '"+this.src+"'.");

		this.onload=null;
		this.onerror=null;
		f_imageRepository._Images[this.src]=false;
		f_imageRepository._ImagesPool.push(this);
	},
	/**
	 * @method private static
	 */
	_OnLoadHandler: function() {
		f_core.Debug(f_imageRepository, "Image '"+this.src+"' loaded.");

		this.onload=null;
		this.onerror=null;
		
		f_imageRepository._Images[this.src]=false;
		f_imageRepository._ImagesPool.push(this);
	},
	/**
	 * @method hidden static
	 * @return void
	 */
	Finalizer: function() {
		var images=f_imageRepository._Images;
		if (!images) {
			return;
		}		
		f_imageRepository._Images=undefined;
		f_imageRepository._ImagesPool=undefined;
		
		for(var url in images) {
			var image=images[url];
			if (!image) {
				continue;
			}
			
			image.onload=null;
			image.onerror=null;
		}
	}
}

new f_class("f_imageRepository", null, __static);
