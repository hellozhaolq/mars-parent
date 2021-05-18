package com.zhaolq.mars.tool.core.io;

import cn.hutool.core.io.IoUtil;

/**
 * IO工具类
 * IO工具类只是辅助流的读写，并不负责关闭流。原因是流可能被多次读写，读写关闭后容易造成问题。
 *
 * @author zhaolq
 * @date 2021/5/18 9:25
 */
public class IoUtils extends IoUtil {

}
