

*在没有root的安卓系统中无效

*在具有root权限的应用中直接调用InstallManager.startTask(context,authToken)方法
会从服务获取安装、卸载内容后台自动安装、卸载。不会弹出对话框

*在没有Root权限的应用中调用startTask方法时，会先申请获取root权限。
弹出对话框若选择总是允许获得root，以后将不会再次弹出对话框


*需要允许开机自启动