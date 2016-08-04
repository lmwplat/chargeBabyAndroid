package com.liumw.chargebaby.utils;

/**
 * 下载数据接口
 * Created by liumw on 2016/8/4 0004.
 */
public interface DownloadCallback  {
    /**
     * 下载前准备
     */
    public void onDownloadPreare();
    /**
     * 下载进度更新
     * @param progress 进度值
     */
    public void onChangeProgress(int progress);
    /**
     * 下载完成
     * @param success  下载成功标示
     * @param errorMsg 下载失败显示内容
     */
    public void onCompleted(boolean success,String errorMsg);
    /**
     * 取消下载
     */
    public boolean onCancel();
}
