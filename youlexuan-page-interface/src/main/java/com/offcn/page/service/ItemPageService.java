package com.offcn.page.service;

public interface ItemPageService {
    /**
     * 生成商品详情页
     *
     * @param goodsIds
     * @return
     */
    public boolean getItemHtml(Long[] goodsIds);
}
