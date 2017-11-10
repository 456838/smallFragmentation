package com.salton123.sf.appstub;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/2/25 16:06
 * Time: 16:06
 * Description:
 */
public class FrescoImageLoader {

    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory() / 4;//分配的可用内存
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;//使用的缓存数量

    public static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 5 * ByteConstants.MB;//小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 10 * ByteConstants.MB;//小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_CACHE_SIZE = 20 * ByteConstants.MB;//小图磁盘缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）

    public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 10 * ByteConstants.MB;//默认图极低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_LOW_SIZE = 30 * ByteConstants.MB;//默认图低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;//默认图磁盘缓存的最大值


    private static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "imagepipeline_cache";//小图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";//默认图所放路径的文件夹名

    private static ImagePipelineConfig sImagePipelineConfig;


    /**
     * 初始化配置，单例
     */
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            sImagePipelineConfig = configureCaches(context);
        }
        return sImagePipelineConfig;
    }

    public static void Init(Context p_Context) {
        Fresco.initialize(p_Context, getImagePipelineConfig(p_Context));// 图片缓存初始化配置
    }

    //直接加载网络图片
    public static void displayImage(String p_Url, DraweeView p_DraweeView, int placeholderImage, int failureImage) {
        if (!TextUtils.isEmpty(p_Url)) {
            DraweeController _controller = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setLowResImageRequest(ImageRequest.fromUri(p_Url))
                    .setUri(p_Url)
                    .build();
            GenericDraweeHierarchy hierarchy = (GenericDraweeHierarchy) p_DraweeView.getHierarchy();
            hierarchy.setPlaceholderImage(placeholderImage);
            hierarchy.setFailureImage(failureImage);
            p_DraweeView.setController(_controller);
        } else {
//            throw new RuntimeException("p_Url 不能为空");
        }
    }


    public static void displayLocalThumbnailImage(DraweeView p_DraweeView, String p_LocalFilePath, int p_NewWidth, int p_NewHeight) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse("file:///" + p_LocalFilePath))
                .setResizeOptions(new ResizeOptions(p_NewWidth, p_NewHeight))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(p_DraweeView.getController())
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        p_DraweeView.setController(controller);
    }

    public static void displayThumbnailImage(DraweeView p_DraweeView, String p_FilePath) {
        if (!TextUtils.isEmpty(p_FilePath)) {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(p_FilePath))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(p_DraweeView.getController())
                    .setImageRequest(request)
                    .setAutoPlayAnimations(true)
                    .build();
            p_DraweeView.setController(controller);
        }
    }

    public static void displayThumbnailImage(DraweeView p_DraweeView, String p_FilePath, int p_NewWidth, int p_NewHeight) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(p_FilePath))
                .setResizeOptions(new ResizeOptions(p_NewWidth, p_NewHeight))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(p_DraweeView.getController())
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        p_DraweeView.setController(controller);
    }


    public static void displayInCenterInside(Context p_Context, DraweeView p_DraweeView, String p_Url) {
        GenericDraweeHierarchyBuilder _builder = new GenericDraweeHierarchyBuilder(p_Context.getResources());
        _builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setFadeDuration(1000)
                .setProgressBarImage(new ProgressBarDrawable());
//                .setRoundingParams(RoundingParams.asCircle());
//        p_SimpleDraweeView.setHierarchy(_builder.build());


        DraweeController _controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setLowResImageRequest(ImageRequest.fromUri(p_Url))
                .setImageRequest(ImageRequest.fromUri(p_Url))
                .setUri(p_Url)
                .setOldController(p_DraweeView.getController())
                .build();
        p_DraweeView.setController(_controller);
    }


    public static void display(DraweeView p_DraweeView, String p_Url) {
        if (!TextUtils.isEmpty(p_Url)) {
            DraweeController _controller = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setTapToRetryEnabled(true)
                    .setLowResImageRequest(ImageRequest.fromUri(p_Url))
                    .setUri(p_Url)
//                    .setOldController(p_DraweeView.getController())
                    .build();
            p_DraweeView.setController(_controller);
        } else {

        }
//        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png");
//        p_DraweeView.setImageURI(uri);
    }


    /**
     * 初始化配置
     */
    private static ImagePipelineConfig configureCaches(Context context) {
        //内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                FrescoImageLoader.MAX_MEMORY_CACHE_SIZE, // 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,                     // 内存缓存中图片的最大数量。
                FrescoImageLoader.MAX_MEMORY_CACHE_SIZE, // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,                     // 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);                    // 内存缓存中单个图片的最大大小。

        //修改内存图片缓存数量，空间策略（这个方式有点恶心）
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

        //小图片的磁盘配置
        DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(context.getApplicationContext().getCacheDir())//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)//文件夹名
//    		  .setCacheErrorLogger(cacheErrorLogger)//日志记录器用于日志错误的缓存。
//    		  .setCacheEventListener(cacheEventListener)//缓存事件侦听器。
//    		  .setDiskTrimmableRegistry(diskTrimmableRegistry)//类将包含一个注册表的缓存减少磁盘空间的环境。
                .setMaxCacheSize(FrescoImageLoader.MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)//缓存的最大大小,当设备极低磁盘空间
//    		  .setVersion(version)
                .build();

        //默认图片的磁盘配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory().getAbsoluteFile())//缓存图片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)//文件夹名
//		      .setCacheErrorLogger(cacheErrorLogger)//日志记录器用于日志错误的缓存。
//            .setCacheEventListener(cacheEventListener)//缓存事件侦听器。
//		      .setDiskTrimmableRegistry(diskTrimmableRegistry)//类将包含一个注册表的缓存减少磁盘空间的环境。
                .setMaxCacheSize(FrescoImageLoader.MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
//		      .setVersion(version)
                .build();

        //缓存图片配置
        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)
//    		  .setAnimatedImageFactory(AnimatedImageFactory animatedImageFactory)//图片加载动画
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)//内存缓存配置（一级缓存，已解码的图片）
//            .setCacheKeyFactory(cacheKeyFactory)//缓存Key工厂
//        	  .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)//内存缓存和未解码的内存缓存的配置（二级缓存）
//        	  .setExecutorSupplier(executorSupplier)//线程池配置
//        	  .setImageCacheStatsTracker(imageCacheStatsTracker)//统计缓存的命中率
//        	  .setImageDecoder(ImageDecoder imageDecoder) //图片解码器配置
//        	  .setIsPrefetchEnabledSupplier(Supplier<Boolean> isPrefetchEnabledSupplier)//图片预览（缩略图，预加载图等）预加载到文件缓存
                .setMainDiskCacheConfig(diskCacheConfig)//磁盘缓存配置（总，三级缓存）
//        	  .setMemoryTrimmableRegistry(memoryTrimmableRegistry) //内存用量的缩减,有时我们可能会想缩小内存用量。比如应用中有其他数据需要占用内存，不得不把图片缓存清除或者减小 或者我们想检查看看手机是否已经内存不够了。
//        	  .setNetworkFetchProducer(networkFetchProducer)//自定的网络层配置：如OkHttp，Volley
//        	  .setPoolFactory(poolFactory)//线程池工厂配置
//        	  .setProgressiveJpegConfig(progressiveJpegConfig)//渐进式JPEG图
//        	  .setRequestListeners(requestListeners)//图片请求监听
//        	  .setResizeAndRotateEnabledForNetwork(boolean resizeAndRotateEnabledForNetwork)//调整和旋转是否支持网络图片
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)//磁盘缓存配置（小图片，可选～三级缓存的小图优化缓存）
                ;
        return configBuilder.build();
    }

    //圆形，圆角切图，对动图无效
    public static RoundingParams getRoundingParams() {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
//	  roundingParams.asCircle();//圆形
//	  roundingParams.setBorder(color, width);//fresco:roundingBorderWidth="2dp"边框  fresco:roundingBorderColor="@color/border_color"
//	  roundingParams.setCornersRadii(radii);//半径
//	  roundingParams.setCornersRadii(topLeft, topRight, bottomRight, bottomLeft)//fresco:roundTopLeft="true" fresco:roundTopRight="false" fresco:roundBottomLeft="false" fresco:roundBottomRight="true"
//	  roundingParams. setCornersRadius(radius);//fresco:roundedCornerRadius="1dp"圆角
//	  roundingParams.setOverlayColor(overlayColor);//fresco:roundWithOverlayColor="@color/corner_color"
//	  roundingParams.setRoundAsCircle(roundAsCircle);//圆
//	  roundingParams.setRoundingMethod(roundingMethod);
//	  fresco:progressBarAutoRotateInterval="1000"自动旋转间隔
        // 或用 fromCornersRadii 以及 asCircle 方法
        return roundingParams;
    }

    //SimpleDraweeControllerBuilder
    public static SimpleDraweeControllerBuilder getSimpleDraweeControllerBuilder(SimpleDraweeControllerBuilder sdcb, Uri uri, Object callerContext, DraweeController draweeController) {
        SimpleDraweeControllerBuilder controllerBuilder = sdcb
                .setUri(uri)
                .setCallerContext(callerContext)
//				.setAspectRatio(1.33f);//宽高缩放比
                .setOldController(draweeController);
        return controllerBuilder;
    }

    //图片解码
    public static ImageDecodeOptions getImageDecodeOptions() {
        ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
//			  .setBackgroundColor(Color.TRANSPARENT)//图片的背景颜色
//			  .setDecodeAllFrames(decodeAllFrames)//解码所有帧
//			  .setDecodePreviewFrame(decodePreviewFrame)//解码预览框
//			  .setForceOldAnimationCode(forceOldAnimationCode)//使用以前动画
//			  .setFrom(options)//使用已经存在的图像解码
//			  .setMinDecodeIntervalMs(intervalMs)//最小解码间隔（分位单位）
                .setUseLastFrameForPreview(true)//使用最后一帧进行预览
                .build();
        return decodeOptions;
    }

    //图片显示
    public static ImageRequest getImageRequest(SimpleDraweeView view, String uri) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
//			  .setAutoRotateEnabled(true)//自动旋转图片方向
//			  .setImageDecodeOptions(getImageDecodeOptions())//  图片解码库
//			  .setImageType(ImageType.SMALL)//图片类型，设置后可调整图片放入小图磁盘空间还是默认图片磁盘空间
//			  .setLocalThumbnailPreviewsEnabled(true)//缩略图预览，影响图片显示速度（轻微）
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)//请求经过缓存级别  BITMAP_MEMORY_CACHE，ENCODED_MEMORY_CACHE，DISK_CACHE，FULL_FETCH
//			  .setPostprocessor(postprocessor)//修改图片
//			  .setProgressiveRenderingEnabled(true)//渐进加载，主要用于渐进式的JPEG图，影响图片显示速度（普通）
                .setResizeOptions(new ResizeOptions(view.getLayoutParams().width, view.getLayoutParams().height))//调整大小
//			  .setSource(Uri uri)//设置图片地址
                .build();
        return imageRequest;
    }

    //DraweeController 控制 DraweeControllerBuilder
    public static DraweeController getDraweeController(ImageRequest imageRequest, SimpleDraweeView view, ControllerListener controllerListener) {
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//			  .reset()//重置
                .setAutoPlayAnimations(true)//自动播放图片动画
//			  .setCallerContext(callerContext)//回调
                .setControllerListener(controllerListener)//监听图片下载完毕等
//			  .setDataSourceSupplier(dataSourceSupplier)//数据源
//			  .setFirstAvailableImageRequests(firstAvailableImageRequests)//本地图片复用，可加入ImageRequest数组
                .setImageRequest(imageRequest)//设置单个图片请求～～～不可与setFirstAvailableImageRequests共用，配合setLowResImageRequest为高分辨率的图
//			  .setLowResImageRequest(ImageRequest.fromUri(lowResUri))//先下载显示低分辨率的图
                .setOldController(view.getController())//DraweeController复用
                .setTapToRetryEnabled(true)//点击重新加载图
                .build();
        return draweeController;
    }

}
