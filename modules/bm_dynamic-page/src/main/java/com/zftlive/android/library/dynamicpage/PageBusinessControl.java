/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package com.zftlive.android.library.dynamicpage;

import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.BaseControl;
import com.zftlive.android.library.base.templet.AbsViewTemplet;
import com.zftlive.android.library.dynamicpage.bean.ElementArticleBean;
import com.zftlive.android.library.dynamicpage.bean.ElementBannerCardItemBean;
import com.zftlive.android.library.dynamicpage.bean.ElementButtonLinkBean;
import com.zftlive.android.library.dynamicpage.bean.ElementCopyrightBean;
import com.zftlive.android.library.dynamicpage.bean.ElementDividerBean;
import com.zftlive.android.library.dynamicpage.bean.ElementFloorTitleBean;
import com.zftlive.android.library.dynamicpage.bean.ElementImageBannerBean;
import com.zftlive.android.library.dynamicpage.bean.ElementMarqueeBean;
import com.zftlive.android.library.dynamicpage.bean.ElementViewPagerBean;
import com.zftlive.android.library.dynamicpage.bean.Page;
import com.zftlive.android.library.dynamicpage.bean.PageFloor;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroup;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroupElement;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;
import com.zftlive.android.library.dynamicpage.templet.ArticleViewTemplet;
import com.zftlive.android.library.dynamicpage.templet.ButtonLinkViewTemplet;
import com.zftlive.android.library.dynamicpage.templet.CopyrightViewTemplet;
import com.zftlive.android.library.dynamicpage.templet.DividerViewTemplet;
import com.zftlive.android.library.dynamicpage.templet.FloorTitleViewTemplet;
import com.zftlive.android.library.dynamicpage.templet.HorizontalViewTemplet;
import com.zftlive.android.library.dynamicpage.templet.ImageBannerViewTemplet;
import com.zftlive.android.library.dynamicpage.templet.MarqueeViewTemplet;
import com.zftlive.android.library.dynamicpage.templet.ViewPagerBannerViewTemplet;
import com.zftlive.android.library.dynamicpage.templet.ViewPagerGridIconViewTemplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 动态页面业务控制
 *
 * @author 曾繁添
 * @version 1.0
 */
public class PageBusinessControl extends BaseControl implements IPageConstant {

    /**
     * 日志输出标记
     */
    final static String TAG = PageBusinessControl.class.getSimpleName();

    /**
     * 元素组类型-视图模板映射关系
     */
    public static Map<Integer, Class<? extends AbsViewTemplet>> mTempletMapper = new TreeMap<>();

    /**
     * 分隔符模板类型
     */
    public static int ITEM_TYPE_FLOOR_DIVIDER = 98, ITEM_TYPE_FLOOR_TITLE = 99, ITEM_TYPE_FLOOR_COPYTIGHT = 100;

    /**
     * [元素组-模板类型]映射关系
     */
    static {
        mTempletMapper.put(ITEM_TYPE_TEMPLET_ARTICLE, ArticleViewTemplet.class);
        mTempletMapper.put(ITEM_TYPE_TEMPLET_IMG_BANNER, ImageBannerViewTemplet.class);
        mTempletMapper.put(ITEM_TYPE_TEMPLET_BUTTON_LINK, ButtonLinkViewTemplet.class);
        mTempletMapper.put(ITEM_TYPE_TEMPLET_HOR_IMG_TEXT, HorizontalViewTemplet.class);
        mTempletMapper.put(ITEM_TYPE_TEMPLET_MARQUEE, MarqueeViewTemplet.class);
        mTempletMapper.put(ITEM_TYPE_TEMPLET_VP_BANNER, ViewPagerBannerViewTemplet.class);
        mTempletMapper.put(ITEM_TYPE_TEMPLET_VP_PAGE_GRID, ViewPagerGridIconViewTemplet.class);
    }

    /**
     * 组装[页面-楼层]
     *
     * @return
     */
    public static Page initPageData() {
        Page mPageData = new Page();
        mPageData.hasCopyright = true;
        mPageData.title = "动态页面生成";
        mPageData.pageId = "110";
        mPageData.floorList = new ArrayList<PageFloor>();

        for (int floorIndex = 0; floorIndex < 6; floorIndex++) {
            PageFloor floor = new PageFloor();
            floor.page = mPageData;
            floor.floorId = floorIndex + "";
            floor.index = floorIndex;
            floor.title = "第" + (floorIndex + 1) + "个楼层";
            floor.hasTitle = true;
            floor.hasTitleButtomLine = true;
            floor.groupList = gainFloorGroupList(floorIndex, floor);
            mPageData.floorList.add(floor);
        }
        return mPageData;
    }

    /**
     * 组装[楼层-元素组]
     *
     * @param floor
     */
    public static ArrayList<PageFloorGroup> gainFloorGroupList(int floorIndex, PageFloor floor) {
        ArrayList<PageFloorGroup> groupList = new ArrayList<PageFloorGroup>();
        //不同楼层组装不同数据
        PageFloorGroup group = null;
        //组装[元素组-元素]
        switch (floorIndex) {
            case 0:
                //添加大图片元素组
//                floor.hasTitle = false;
                group = new PageFloorGroup();
                group.floor = floor;
                group.groupId = "0";
                group.groupType = ITEM_TYPE_TEMPLET_IMG_BANNER;
                group.elementList = new ArrayList<PageFloorGroupElement>();
                groupList.add(group);
                //添加元素组元素
                group.elementList.add(gainItemTypeElement(0, ITEM_TYPE_TEMPLET_IMG_BANNER, new PageFloorGroupElement(group)));

                //添加网格元素组
                group = new PageFloorGroup();
                group.floor = floor;
                group.groupId = "1";
                group.groupType = ITEM_TYPE_TEMPLET_VP_PAGE_GRID;
                group.hasTopMargin = true;
                group.elementList = new ArrayList<PageFloorGroupElement>();
                groupList.add(group);
                group.elementList.add(gainItemTypeElement(0, ITEM_TYPE_TEMPLET_VP_PAGE_GRID, new PageFloorGroupElement(group)));

                break;
            case 1:
                //添加楼层元素组
                group = new PageFloorGroup();
//                floor.hasTitle = false;
                group.floor = floor;
                group.groupId = "0";
                group.groupType = ITEM_TYPE_TEMPLET_MARQUEE;
                group.elementList = new ArrayList<PageFloorGroupElement>();
                groupList.add(group);

                //添加元素组元素
                group.elementList.add(gainItemTypeElement(0, ITEM_TYPE_TEMPLET_MARQUEE, new PageFloorGroupElement(group)));
                break;
            case 2:
                //添加楼层元素组
//                floor.hasTitle = false;

                group = new PageFloorGroup();
                group.floor = floor;
                group.groupId = "0";
                group.groupType = ITEM_TYPE_TEMPLET_VP_BANNER;
                group.elementList = new ArrayList<PageFloorGroupElement>();
                groupList.add(group);

                //添加元素组元素
                group.elementList.add(gainItemTypeElement(0, ITEM_TYPE_TEMPLET_VP_BANNER, new PageFloorGroupElement(group)));
                break;
            case 3:
                //添加楼层元素组
                group = new PageFloorGroup();
                floor.title = (floorIndex + 1) + "-醉美夕阳";
                floor.hasButtomMargin = true;
                group.floor = floor;
                group.groupId = "0";
                group.groupType = ITEM_TYPE_TEMPLET_HOR_IMG_TEXT;
                group.elementList = new ArrayList<PageFloorGroupElement>();
                groupList.add(group);

                //添加元素组元素
                group.elementList.add(gainItemTypeElement(0, ITEM_TYPE_TEMPLET_HOR_IMG_TEXT, new PageFloorGroupElement(group)));
                break;
            case 4:
                //添加楼层元素组
                group = new PageFloorGroup();
                floor.title = (floorIndex + 1) + "-财经资讯";
                floor.hasTitleButtomLine = true;
                floor.subTitle = "更多";
                floor.subTitleIcon = "http://zftlive-images.qiniudn.com/anl_right_arrow_normal.png";
                floor.floorTileJumpData = new Forward(0, "http://www.baidu.com");
                group.floor = floor;
                group.groupId = "0";
                group.groupType = ITEM_TYPE_TEMPLET_ARTICLE;
                group.elementList = new ArrayList<PageFloorGroupElement>();
                groupList.add(group);

                //添加元素组元素
                for (int i = 0; i < 100; i++) {
                    group.elementList.add(gainItemTypeElement(i, ITEM_TYPE_TEMPLET_ARTICLE, new PageFloorGroupElement(group)));
                }

                //查看更多按钮
                group = new PageFloorGroup();
                group.floor = floor;
                group.groupId = "1";
                group.groupType = ITEM_TYPE_TEMPLET_BUTTON_LINK;
                group.elementList = new ArrayList<PageFloorGroupElement>();
                groupList.add(group);
                group.elementList.add(gainItemTypeElement(6, ITEM_TYPE_TEMPLET_BUTTON_LINK, new PageFloorGroupElement(group)));
                break;
            default:
                break;
        }
        return groupList;
    }

    /**
     * 组装各种type对应的元素
     *
     * @param groupType
     * @param elementItem
     */
    public static PageFloorGroupElement gainItemTypeElement(int index, int groupType, PageFloorGroupElement elementItem) {
        switch (groupType) {
            case ITEM_TYPE_TEMPLET_ARTICLE:
                elementItem.article = new ElementArticleBean();
                elementItem.article.group = elementItem.group;
                elementItem.article.authorImg = "http://zftlive-images.qiniudn.com/avtar-300x300.jpg";
                elementItem.article.authorName = "Ajava攻城师";
                elementItem.article.releaseDT = "30分钟之前";
                elementItem.article.title = (index + 1) + "东方时尚驾校-C1驾驶证";
                elementItem.article.summary = (index + 1) + "侧方和倒车入库都跟坐姿很大关系，调整座椅和镜子尤其重要！座椅和镜子尤其重要！座椅和镜子尤其重要！";
                elementItem.article.thumb = "http://img.ivsky.com/img/tupian/t/201610/10/qiuri_de_fengjing.jpg";
                elementItem.article.tag = "驾驶证";
                break;
            case ITEM_TYPE_TEMPLET_IMG_BANNER:
                elementItem.picBanner = new ElementImageBannerBean("http://t2.27270.com/uploads/tu/201611/198/aaszlje2tlw.jpg");
                elementItem.picBanner.group = elementItem.group;
                break;
            case ITEM_TYPE_TEMPLET_BUTTON_LINK:
                elementItem.buttonLink = new ElementButtonLinkBean("", "发现更多", "#508cee");
                elementItem.buttonLink.group = elementItem.group;
                break;
            case ITEM_TYPE_TEMPLET_VP_BANNER:
                elementItem.vpData = new ElementViewPagerBean();
                elementItem.vpData.dotDirection = DIRECTION_LEFT;//指示器在左边
                elementItem.vpData.itemList = new ArrayList<ElementBannerCardItemBean>();
                elementItem.vpData.itemList.add(new ElementBannerCardItemBean(elementItem.group, "http://tupian.qqjay.com/u/2013/1127/19_222949_14.jpg", "美腻了 风景图1"));
                elementItem.vpData.itemList.add(new ElementBannerCardItemBean(elementItem.group, "http://tupian.qqjay.com/u/2013/1127/19_222949_3.jpg", "美腻了 风景图写真2"));
                elementItem.vpData.itemList.add(new ElementBannerCardItemBean(elementItem.group, "http://tupian.qqjay.com/u/2013/1127/19_222949_4.jpg", "夏茉美腻了 风景图写真3"));
                elementItem.vpData.itemList.add(new ElementBannerCardItemBean(elementItem.group, "http://tupian.qqjay.com/u/2013/1127/19_222949_6.jpg", "夏茉美腻了 风景图写真4"));
                elementItem.vpData.itemList.add(new ElementBannerCardItemBean(elementItem.group, "http://tupian.qqjay.com/u/2013/1127/19_222949_9.jpg", "夏茉美腻了 风景图写真5"));
                elementItem.vpData.itemList.add(new ElementBannerCardItemBean(elementItem.group, "http://tupian.qqjay.com/u/2013/1127/19_222949_11.jpg", "夏茉美腻了 风景图写真6"));
                elementItem.vpData.group = elementItem.group;
                break;
            case ITEM_TYPE_TEMPLET_VP_PAGE_GRID:
                elementItem.vpData = new ElementViewPagerBean();
                elementItem.vpData.dotDirection = DIRECTION_CENTER;//指示器在中间
                //二维数据
                ArrayList<ArrayList<ElementBannerCardItemBean>> mPageList = new ArrayList<>();
                //循环页数
                for (int i = 0; i < 3; i++) {
                    //每一页item数据
                    ArrayList<ElementBannerCardItemBean> mItemList = new ArrayList<>();
                    for (int j = 0; j < 8; j++) {
                        mItemList.add(new ElementBannerCardItemBean(elementItem.group, "http://www.iconpng.com/png/humanitarian/cart7.png", "菜单" + i + "-" + j, COLOR_666666));
                    }
                    mPageList.add(mItemList);
                }
                elementItem.vpData.pageItemList = mPageList;
                elementItem.vpData.group = elementItem.group;
                break;
            case ITEM_TYPE_TEMPLET_HOR_IMG_TEXT:
                elementItem.horScrollDataList = new ArrayList<ElementBannerCardItemBean>();
                elementItem.horScrollDataList.add(new ElementBannerCardItemBean(elementItem.group, "http://img.ivsky.com/img/tupian/t/201610/21/zuimei_xiyang.jpg", "醉美夕阳1", COLOR_666666));
                elementItem.horScrollDataList.add(new ElementBannerCardItemBean(elementItem.group, "http://img.ivsky.com/img/tupian/t/201610/21/zuimei_xiyang-001.jpg", "醉美夕阳2", COLOR_666666));
                elementItem.horScrollDataList.add(new ElementBannerCardItemBean(elementItem.group, "http://img.ivsky.com/img/tupian/t/201610/21/zuimei_xiyang-002.jpg", "醉美夕阳3", COLOR_666666));
                elementItem.horScrollDataList.add(new ElementBannerCardItemBean(elementItem.group, "http://img.ivsky.com/img/tupian/t/201610/21/zuimei_xiyang-003.jpg", "醉美夕阳4", COLOR_666666));
                elementItem.horScrollDataList.add(new ElementBannerCardItemBean(elementItem.group, "http://img.ivsky.com/img/tupian/t/201610/21/zuimei_xiyang-004.jpg", "醉美夕阳5", COLOR_666666));
                elementItem.horScrollDataList.add(new ElementBannerCardItemBean(elementItem.group, "http://img.ivsky.com/img/tupian/t/201610/21/zuimei_xiyang-006.jpg", "醉美夕阳6", COLOR_666666));
                elementItem.horScrollDataList.add(new ElementBannerCardItemBean(elementItem.group, "http://img.ivsky.com/img/tupian/t/201610/21/zuimei_xiyang-008.jpg", "醉美夕阳7", COLOR_666666));
                elementItem.horScrollDataList.add(new ElementBannerCardItemBean(elementItem.group, "http://img.ivsky.com/img/tupian/t/201610/21/zuimei_xiyang-010.jpg", "醉美夕阳8", COLOR_666666));
                break;
            case ITEM_TYPE_TEMPLET_MARQUEE:
                elementItem.marqueeData = new ArrayList<ElementMarqueeBean>();
                for (int i = 0; i < 10; i++) {
                    elementItem.marqueeData.add(new ElementMarqueeBean(elementItem.group, "左标题" + (i + 1), "右标题" + (i + 1)));
                }
                break;
            default:
                break;
        }

        return elementItem;
    }

    /**
     * 追加固定的itemType类型
     *
     * @param pageData
     */
    public static void appendFixedItemType(Page pageData){
        //动态构建模板-视图映射关系
        List<Integer> mGroupTypeList = new ArrayList<>();
        for (int floorIndex = 0; floorIndex < pageData.floorList.size(); floorIndex++) {
            PageFloor floor = pageData.floorList.get(floorIndex);
            if (null == floor.groupList || floor.groupList.isEmpty()) {
                continue;
            }
            for (int groupIndex = 0; groupIndex < floor.groupList.size(); groupIndex++) {
                mGroupTypeList.add(floor.groupList.get(groupIndex).groupType);
            }
        }
        int maxGroupType = Collections.max(mGroupTypeList);
        //内置固定拆分模板
        ITEM_TYPE_FLOOR_TITLE = maxGroupType + 1;
        ITEM_TYPE_FLOOR_DIVIDER = maxGroupType + 2;
        ITEM_TYPE_FLOOR_COPYTIGHT = maxGroupType + 3;
        mTempletMapper.put(ITEM_TYPE_FLOOR_TITLE, FloorTitleViewTemplet.class);
        mTempletMapper.put(ITEM_TYPE_FLOOR_DIVIDER, DividerViewTemplet.class);
        mTempletMapper.put(ITEM_TYPE_FLOOR_COPYTIGHT, CopyrightViewTemplet.class);
        Logger.d(TAG, "Collections.max(mGroupTypeList)=" + Collections.max(mGroupTypeList));
        Logger.d(TAG, "ITEM_TYPE_FLOOR_TITLE=" + ITEM_TYPE_FLOOR_TITLE + " ITEM_TYPE_FLOOR_DIVIDER=" + ITEM_TYPE_FLOOR_DIVIDER + " ITEM_TYPE_FLOOR_COPYTIGHT=" + ITEM_TYPE_FLOOR_COPYTIGHT);
    }

    /**
     * 转换页面数据为listview的item类型数据
     *
     * @param pageData
     * @return
     */
    public static ArrayList<PageListElementBean> convertPageDataToListItem(Page pageData) {
        //返回结果集
        ArrayList<PageListElementBean> listItems = new ArrayList<>();
        //数据合法性检查
        if (null == pageData || null == pageData.floorList || pageData.floorList.isEmpty()) {
            return listItems;
        }
        //过滤没有元素的楼层
        Iterator<PageFloor> tabIt = pageData.floorList.iterator();
        int index = 0;
        while (tabIt.hasNext()) {
            PageFloor tab = tabIt.next();
            //TODO 是否考虑判断是否含有标题+楼层上下顶部间隙这个元素?
            if (tab.groupList == null || tab.groupList.isEmpty()) {
                Logger.w(TAG, "第" + index + "个楼层没有配置任何元素，从页面楼层中移除");
                tabIt.remove();
                continue;
            }
            index++;
        }
        //追加固定类型
        PageBusinessControl.appendFixedItemType(pageData);

        //楼层第一个分隔符
        if (pageData.hasFirstDivider) {
            listItems.add(new PageListElementBean(pageData, ITEM_TYPE_FLOOR_DIVIDER, new ElementDividerBean()));
        }
        //拆分楼层
        for (int i = 0; i < pageData.floorList.size(); i++) {
            PageFloor pageFloor = pageData.floorList.get(i);
            //拆分楼层元素组
            convertPageFloorGroup(i, listItems, pageData, pageFloor);
        }
        //版权信息
        if (pageData.hasCopyright) {
            listItems.add(new PageListElementBean(pageData, ITEM_TYPE_FLOOR_COPYTIGHT, new ElementCopyrightBean("- Copyright by Ajava 攻 城 师 -")));
        }
        return listItems;
    }

    /**
     * 拆分楼层元素组
     *
     * @param floorIndex 当前楼层索引
     * @param listItems  当前listview的item集合
     * @param pageData   当前页面
     * @param pageFloor  当前楼层
     */
    private static void convertPageFloorGroup(int floorIndex, ArrayList<PageListElementBean> listItems, Page pageData, PageFloor pageFloor) {
        if (null == pageFloor || null == pageFloor.groupList || pageFloor.groupList.isEmpty()) {
            //TODO 是否需要考虑标题渲染?直接return
            Logger.w(TAG, "--convertPageFloorGroup--当前页面的第" + floorIndex + "个楼层元素组为空，跳过本次遍历");
            return;
        }

        //楼层顶部间隙
        if (pageFloor.hasTopMargin) {
            listItems.add(new PageListElementBean(pageData, ITEM_TYPE_FLOOR_DIVIDER, new ElementDividerBean(pageFloor.backgroundColor)));
        }

        //楼层标题
        if (pageFloor.hasTitle) {
            ElementFloorTitleBean itemTitle = new ElementFloorTitleBean(pageFloor.title, pageFloor.subTitle);
            itemTitle.title = pageFloor.title;
            itemTitle.titleIcon = pageFloor.titleIcon;
            itemTitle.titleTextColor = pageFloor.titleTextColor;
            itemTitle.subTitle = pageFloor.subTitle;
            itemTitle.subTitleIcon = pageFloor.subTitleIcon;
            itemTitle.subTitleTextColor = pageFloor.subTitleTextColor;
            itemTitle.backgroundColor = pageFloor.backgroundColor;
            itemTitle.hasTitleButtomLine = pageFloor.hasTitleButtomLine;
            listItems.add(new PageListElementBean(pageData, ITEM_TYPE_FLOOR_TITLE, itemTitle));
        }
        //楼层元素组
        for (int i = 0; i < pageFloor.groupList.size(); i++) {
            PageFloorGroup pageFloorGroup = pageFloor.groupList.get(i);
            convertPageFloorGroupElement(i, listItems, pageData, pageFloor, pageFloorGroup, i);
        }
        //楼层底部间隙
        if (pageFloor.hasButtomMargin) {
            listItems.add(new PageListElementBean(pageData, ITEM_TYPE_FLOOR_DIVIDER, new ElementDividerBean(pageFloor.backgroundColor)));
        }

        //楼层之间的间隔符，最后一个楼层是否需要看间隔
        if (!pageData.hasLastDivider && (floorIndex == pageData.floorList.size() - 1)) {
            return;
        }
        listItems.add(new PageListElementBean(pageData, ITEM_TYPE_FLOOR_DIVIDER, new ElementDividerBean()));
    }

    /**
     * 拆分楼层元素组元素集合
     *
     * @param floorIndex      当前楼层索引
     * @param listItems       当前listview的item集合
     * @param pageData        当前页面
     * @param pageFloor       当前楼层
     * @param pageFloorGroup  当前楼层元素组
     * @param floorGroupIndex 楼层元素组索引
     */
    private static void convertPageFloorGroupElement(int floorIndex, ArrayList<PageListElementBean> listItems, Page pageData, PageFloor pageFloor, PageFloorGroup pageFloorGroup, int floorGroupIndex) {
        if (null == pageFloorGroup || null == pageFloorGroup.elementList || pageFloorGroup.elementList.isEmpty()) {
            Logger.w(TAG, "--convertPageFloorGroupElement--当前页面的第" + floorIndex + "个楼层第" + floorGroupIndex + "个元素组下的元素集合为空，跳过本次遍历");
            return;
        }

        //楼层元素组顶部间隙
        if (pageFloorGroup.hasTopMargin) {
            listItems.add(new PageListElementBean(pageData, ITEM_TYPE_FLOOR_DIVIDER, new ElementDividerBean(COLOR_FFFFFF)));
        }

        //当前元素组类型
        int groupType = pageFloorGroup.groupType;
        for (int i = 0; i < pageFloorGroup.elementList.size(); i++) {
            if (!mTempletMapper.containsKey(groupType)) {
                Logger.w(TAG, "--convertPageFloorGroupElement--元素组类型" + groupType + "不在本地枚举范围之内，终止第" + i + "次添加元素类型循环");
                continue;
            }
            PageFloorGroupElement elementItem = pageFloorGroup.elementList.get(i);
            listItems.add(new PageListElementBean(pageData, groupType, pageFloor, pageFloorGroup, elementItem));
        }

        //楼层元素组底部间隙
        if (pageFloorGroup.hasButtomMargin) {
            listItems.add(new PageListElementBean(pageData, ITEM_TYPE_FLOOR_DIVIDER, new ElementDividerBean(COLOR_FFFFFF)));
        }
    }
}
