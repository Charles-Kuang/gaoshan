package com.ruoyi.applet.service.impl;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.QRCodeUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.applet.mapper.GsAddressHouseMapper;
import com.ruoyi.common.core.domain.entity.GsAddressHouse;
import com.ruoyi.applet.service.IGsAddressHouseService;

import javax.servlet.http.HttpServletResponse;

/**
 * addressHouseService业务层处理
 *
 * @author ruoyi
 * @date 2022-12-12
 */
@Slf4j
@Service
public class GsAddressHouseServiceImpl implements IGsAddressHouseService {
    @Autowired
    GsAddressHouseMapper gsAddressHouseMapper;

    /**
     * 查询addressHouse
     *
     * @param id addressHouseID
     * @return addressHouse
     */
    @Override
    public GsAddressHouse selectGsAddressHouseById(Long id) {
        return gsAddressHouseMapper.selectGsAddressHouseById(id);
    }

    /**
     * 查询addressHouse列表
     *
     * @param gsAddressHouse addressHouse
     * @return addressHouse
     */
    @Override
    public List<GsAddressHouse> selectGsAddressHouseList(GsAddressHouse gsAddressHouse) {
        return gsAddressHouseMapper.selectGsAddressHouseList(gsAddressHouse);
    }

    /**
     * 新增addressHouse
     *
     * @param gsAddressHouse addressHouse
     * @return 结果
     */
    @Override
    public int insertGsAddressHouse(GsAddressHouse gsAddressHouse) {
        gsAddressHouse.setQrCode(QRCodeUtil.createCodeToFile(IdUtils.fastUUID()));
        gsAddressHouse.setCreateTime(DateUtils.getNowDate());
        gsAddressHouse.setCreateUser(SecurityUtils.getUsername());
        gsAddressHouse.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsAddressHouse.setCreateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return gsAddressHouseMapper.insertGsAddressHouse(gsAddressHouse);
    }

    /**
     * 修改addressHouse
     *
     * @param gsAddressHouse addressHouse
     * @return 结果
     */
    @Override
    public int updateGsAddressHouse(GsAddressHouse gsAddressHouse) {
        gsAddressHouse.setQrCode(QRCodeUtil.createCodeToFile(IdUtils.fastUUID()));
        gsAddressHouse.setLastUpdateTime(DateUtils.getNowDate());
        gsAddressHouse.setLastUpdateUser(SecurityUtils.getUsername());
        gsAddressHouse.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsAddressHouse.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return gsAddressHouseMapper.updateGsAddressHouse(gsAddressHouse);
    }

    /**
     * 修改addressHouse
     *
     * @param gsAddressHouse addressHouse
     * @return 结果
     */
    @Override
    public int updateGsAddressHouseOne(GsAddressHouse gsAddressHouse) {
        if (gsAddressHouse.getCommitStatus() != 1) {
            gsAddressHouse.setLastUpdateTime(DateUtils.getNowDate());
            gsAddressHouse.setLastUpdateUser(SecurityUtils.getUsername());
            gsAddressHouse.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsAddressHouse.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsAddressHouse.setAuditUser(SecurityUtils.getUsername());
            gsAddressHouse.setAuditUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsAddressHouse.setAuditUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsAddressHouse.setAuditState(3L);
            return gsAddressHouseMapper.updateGsAddressHouse(gsAddressHouse);
        }
        return 0;
    }

    @Override
    public  byte[] createQRCode(Long number, HttpServletResponse response){
        List<GsAddressHouse> photos = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            GsAddressHouse gsAddressHouse = new GsAddressHouse();
            gsAddressHouse.setCreateTime(DateUtils.getNowDate());
            gsAddressHouse.setCreateUser(SecurityUtils.getUsername());
            gsAddressHouse.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsAddressHouse.setCreateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            String s = IdUtils.fastUUID();
            gsAddressHouse.setQrCode(QRCodeUtil.createCodeToFile(s));
           // gsAddressHouseMapper.insertGsAddressHouse(gsAddressHouse);
            photos.add(gsAddressHouse);
        }
        //压缩包名字拼一拼
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        downloadTolocal(zip, photos);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    private void downloadTolocal(ZipOutputStream zos, List<GsAddressHouse> photos){
        // 本地资源路径
        String downloadPath = "";
        // 获取模板列表
        for (GsAddressHouse photo : photos) {
            {
                // 渲染模板
                StringWriter sw = new StringWriter();
                downloadPath = RuoYiConfig.getProfile() + photo.getQrCode().replace("http://localhost:8080/profile", "");
                File file = new File(downloadPath);
                try {
                    // 添加到zip
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    org.apache.commons.io.IOUtils.write(sw.toString(), zos, Constants.UTF8);
                    org.apache.commons.io.IOUtils.closeQuietly(sw);
                    zos.flush();
                    zos.closeEntry();
                } catch (IOException e) {
                    log.error("渲染模板失败，表名：" + file.getName(), e);
                }
            }
        }
    }

        /**
         * 批量删除addressHouse
         *
         * @param ids 需要删除的addressHouseID
         * @return 结果
         */
        @Override
        public int deleteGsAddressHouseByIds (Long[]ids){
            return gsAddressHouseMapper.deleteGsAddressHouseByIds(ids);
        }

    /**
     * 删除addressHouse信息
     * 
     * @param id addressHouseID
     * @return 结果
     */
    @Override
    public int deleteGsAddressHouseById(Long id)
    {
        return gsAddressHouseMapper.deleteGsAddressHouseById(id);
    }
}
