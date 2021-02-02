# 图片加载框架

封装与glide的易用的图片加载框架

- Github: https://github.com/bumptech/glide

## 1.1.2更新

- 更新IO工具到1.0.8版本。

## 1.1.1更新

- 更新IO工具到1.0.7版本。

## 1.1.0更新

- 更新IO工具到1.0.6版本。

## 1.0.9更新

- 修正上个版本产生的严重BUG。

## 1.0.8更新

- 去除掉IO包，之前是我想多了。

## 1.0.7更新

- 1.0.6并没有真正解决问题，1.0.7修正此问题。

## 1.0.6更新

- 修正公共路径下，本地图片无法显示的问题。

## 1.0.5更新

- 兼容Android10公共目录文件加载显示

## 框架引用方法

- 在gradle中:
```gradle
    //封装的主要框架
    implementation 'spa.lyh.cn:lib_image:1.1.2'
    //不引用此框架，会造成加载进度监控失效，缓存无法自动指向app的外置cache目录
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
```

## 引用的主要类

- `api 'com.github.bumptech.glide:glide'` glide本体

- `api 'jp.wasabeef:glide-transformations'` glide自带大部分滤镜

- `annotationProcessor 'com.github.bumptech.glide:compiler'` glide注解框架

- `implementation 'com.squareup.okhttp3:okhttp'` okhttp用来承载glide的网络层

- `implementation 'spa.lyh.cn:lib_io'` okhttp的io库

# 实体类方法说明

## ImageLoadUtil

#### 加载一般图片的方法

- displayImage();

```java
/**
* @param context 上下文
* @param res 资源源路径，无论是String，drawable，bitmap，视频等基本都可以直接传
* @param target ImageView对象
*/
displayImage(Context context, Object res, ImageView target)

/**
* @param option Glide使用的额外选项，用户可以自定义如滤镜，裁剪，占位图等
*/
displayImage(Context context, Object res, ImageView target, RequestOptions option)

/**
* @param signature 图片唯一签名，用来针对后台图片地址不变，但是图片本身会改变的情况，唯一标识符最好是由后台
*                   传过来，使用时间戳就行，或其他唯一标识。
*/
displayImage(Context context, Object res, ImageView target, String signature)

displayImage(Context context, Object res, ImageView target, RequestOptions option, String signature)
```

#### 加载状态栏通知的图片

- displayImageForNotification();

```java
/**
* @param context 上下文
* @param rv 通知栏的view
* @param resId ImageView的resId
* @param notification Notification这个通知栏的对象
* @param NOTIFICATION_ID 状态栏id
* @param url 显示图片路径，不宜尺寸太大
*/
displayImageForNotification(Context context, RemoteViews rv, int resId,Notification notification, int NOTIFICATION_ID, String url)
```

#### 获得图片对应的bitmap的方法

- getImageBitmap();

```java
/**
* @param context 上下文
* @param res 资源源路径，无论是String，drawable，bitmap，视频等基本都可以直接传
* @param target CustomTarget对象，获得对应的bitmap，包含了图片的信息
*/
getImageBitmap(Context context, Object res, CustomTarget<Bitmap> target)

/**
* @param option Glide使用的额外选项，用户可以自定义如滤镜，裁剪，占位图等
*/
getImageBitmap(Context context, Object res, RequestOptions option, CustomTarget<Bitmap> target)
```

## 注意事项

- API引用的依赖库不需要重复依赖，可以直接使用

- Android10公共目录请严格遵守使用规范
