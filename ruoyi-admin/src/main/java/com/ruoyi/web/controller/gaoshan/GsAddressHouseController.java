package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.applet.service.IGsAddressHouseService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.GsAddressHouse;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.web.util.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * addressHouseController
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
@Controller
@RequestMapping("/applet/address_house")
public class GsAddressHouseController extends BaseController
{
    @Autowired
    private IGsAddressHouseService gsAddressHouseService;

    /**
     * 查询addressHouse列表
     */
    @PreAuthorize("@ss.hasPermi('applet:address_house:list')")
    @GetMapping("/list")
    @ResponseBody
    public TableDataInfo list(GsAddressHouse gsAddressHouse)
    {
        startPage();
        List<GsAddressHouse> list = gsAddressHouseService.selectGsAddressHouseList(gsAddressHouse);
        return getDataTable(list);
    }
    @GetMapping("/getAddressMap")
    @ResponseBody
    public void getAddressMap(){
    }
    /**
     * 导出addressHouse列表
     */
    @PreAuthorize("@ss.hasPermi('applet:address_house:export')")
    @Log(title = "addressHouse", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ResponseBody
    public AjaxResult export(GsAddressHouse gsAddressHouse)
    {
        List<GsAddressHouse> list = gsAddressHouseService.selectGsAddressHouseList(gsAddressHouse);
        ExcelUtil<GsAddressHouse> util = new ExcelUtil<GsAddressHouse>(GsAddressHouse.class);
        return util.exportExcel(list, "address_house");
    }

    /**
     * 获取addressHouse详细信息
     */
    @PreAuthorize("@ss.hasPermi('applet:address_house:query')")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gsAddressHouseService.selectGsAddressHouseById(id));
    }

    /**
     * 新增addressHouse
     */
    @PreAuthorize("@ss.hasPermi('applet:address_house:add')")
    @Log(title = "addressHouse", businessType = BusinessType.INSERT)
    @PostMapping
    @ResponseBody
    public AjaxResult add(@RequestBody GsAddressHouse gsAddressHouse)
    {
        return toAjax(gsAddressHouseService.insertGsAddressHouse(gsAddressHouse));
    }

    /**
     * 修改addressHouse
     */
    @PreAuthorize("@ss.hasPermi('applet:address_house:edit')")
    @Log(title = "addressHouse", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResponseBody
    public AjaxResult edit(@RequestBody GsAddressHouse gsAddressHouse)
    {
        return toAjax(gsAddressHouseService.updateGsAddressHouse(gsAddressHouse));
    }

    /**
     * 修改addressHouse
     */
    @Log(title = "addressHouse", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    @ResponseBody
    public AjaxResult editOne(@RequestBody GsAddressHouse gsAddressHouse)
    {
        return toAjax(gsAddressHouseService.updateGsAddressHouseOne(gsAddressHouse));
    }

    /**
     * 修改addressHouse
     */
//    @GetMapping("/createQrcode/{number}")
//    public void editTwo(@PathVariable("number") Long number,  HttpServletResponse response) throws Exception
//    {
//        byte[] qrCode = gsAddressHouseService.createQRCode(number, response);
//        genCode(response, qrCode);
//    }
    @RequestMapping("/createQrcode/{number}")
    public void downloadAll(@PathVariable("number") Integer number,HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("application/zip");
        String zipName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".zip";
        response.setHeader("Content-disposition","attachment; filename="+zipName);
        OutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        for(int i = 0; i < number; i++){
            String uuid=UUID.randomUUID().toString().replaceAll("-","");
            BufferedImage image = QRCodeUtil.createImage(uuid,null);
            ZipEntry entry = new ZipEntry(String.valueOf(System.currentTimeMillis())+"."+QRCodeUtil.FORMAT_NAME);
            zipOutputStream.putNextEntry(entry);
            ImageIO.write(image, QRCodeUtil.FORMAT_NAME, zipOutputStream);
            zipOutputStream.flush();
        }
        zipOutputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 删除addressHouse
     */
    @PreAuthorize("@ss.hasPermi('applet:address_house:remove')")
    @Log(title = "addressHouse", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    @ResponseBody
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gsAddressHouseService.deleteGsAddressHouseByIds(ids));
    }
}
