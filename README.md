# CurrentActivity

**CurrentActivity** 是用于显示当前 **应用包名** 和 **Activity类名** 的Android应用程序。

### 安装包地址：

[CurrentActivity-debug.apk](https://github.com/sinawangnan7/CurrentActivity/blob/master/CurrentActivity-debug.apk)

### 使用说明:

**1.打开方式（开启“辅助服务”）** 如下图所示：

![image_open.png](https://github.com/sinawangnan7/CurrentActivity/blob/master/app/image/image_open.png)

（注：Android 6.0以上 **必须** 开启悬浮窗权限，通知栏权限建议开启）

**2.隐藏/显示悬浮窗（前提：已开启辅助服务）**

![image_switch.png](https://github.com/sinawangnan7/CurrentActivity/blob/master/app/image/image_switch.png)

**3.关闭方式（关闭“辅助服务”）** 如下图所示：

![image_close.png](https://github.com/sinawangnan7/CurrentActivity/blob/master/app/image/image_close.png)

注：Android 7.0以上如果APP有通知栏权限，可通过 **点击通知栏** 关闭辅助服务（如下图所示）

![image_notify.png](https://github.com/sinawangnan7/CurrentActivity/blob/master/app/image/image_notify.png)

### 其他说明：

1.CurrentActivity显示的是顶层窗口视图的类名。所以，不一定是Activity类名，也有可能是Dialog或View的类名。

2.CurrentActivity在某些低版本的手机或模拟器上，可能会出现应用退出窗口被移除的Bug，但继续切换应用，窗口会重新显示出来。

3. Android应用市场已有此功能的应用，该项目功能参考 [应用宝 - 当前Activity](http://sj.qq.com/myapp/search.htm?kw=%E5%BD%93%E5%89%8DActiity)。

4. 该项目仅用于参考学习，辅助开发。
