使用说明：这是一个生成多渠道包的python脚本，运行脚本之前请确保系统安装了Python环境：
1、手动打好的APK放置当前目录
2、需要生成的渠道号写入./info/channel_list.txt文本中，多个渠道号换行写入
3、双击MultiChannelBuildTool.py会生成[output_工程名]的文件夹
4、验证生成的渠道号是否正确，可以使用ToolChannel.gainChannel(mContext, "ChannelName")校验，该类提供了获取渠道号的方法

原理解析：
脚本利用了APK压缩包中META-INF文件夹不参与签名的原理，拿到手动打好的发布APK进行解压，遍历/info/channel_list.txt的渠道号集合，
往各个渠道APK的META-INF目录写入一个[ChannelName_渠道号]的空文件。所以渠道号配置不是在AndroidManifest.xml配置一个meta-data了，这里要注意！
项目中各种渠道统计需要的渠道号可以用ToolChannel.gainChannel(mContext, "ChannelName")函数获取，渠道号的key-->"ChannelName"不是固定的
可以自行修改MultiChannelBuildTool.py第54行代码.总之，如果要和python脚本代码保持一致（其实就一个key，没必要修改，以免造成不必要的麻烦）

参考资料：
windows系统下Python环境的搭建 http://www.cnblogs.com/windinsky/archive/2012/09/20/2695520.html
Eclipse配置PyDev插件 http://www.cnblogs.com/halfacre/archive/2012/07/22/2603848.html