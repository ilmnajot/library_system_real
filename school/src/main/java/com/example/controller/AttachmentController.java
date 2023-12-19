package com.example.controller;

import com.example.entity.Attachment;
import com.example.entity.AttachmentContent;
import com.example.model.common.ApiResponse;
import com.example.repository.AttachmentContentRepository;
import com.example.repository.AttachmentRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/attachment")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentRepository attachmentRepository;

    private final AttachmentContentRepository attachmentContentRepository;

    /**
     * YANGI FILE YOKI RASM QO'SHISH
     *
     * @param request FILE KELADI
     * @return ApiResponse(message - > FILE SUCCESSFULLY SAVED, success - > true)
     * @throws IOException
     */
    @PostMapping("/upload")
    public ApiResponse uploadFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();
            Attachment attachment = new Attachment();
            attachment.setFileOriginalName(originalFilename);
            attachment.setSize(size);
            attachment.setContentType(contentType);
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setId(attachment.getId());
            attachmentContent.setMainContent(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            attachmentContentRepository.save(attachmentContent);
            return new ApiResponse("FILE SUCCESSFULLY SAVED", true, attachment);

        }
        return new ApiResponse("Error", false);
    }

    /**
     * FILE INFOLARINI KO'RISH
     *
     * @param response
     * @return
     */
    @GetMapping("/info")
    public List<Attachment> getInfo(HttpServletResponse response) {
        List<Attachment> all = attachmentRepository.findAll();
        return all;
    }

    /**
     * ID ORQALI FILE MALUMOTLAARINI KO'RISH
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/info/{id}")
    public Attachment getInfo(@PathVariable Integer id, HttpServletResponse response) {
        Optional<Attachment> byId = attachmentRepository.findById(id);
        return byId.orElse(null);
    }

    /**
     * ID ORQALI RASMNI YUKLASH
     *
     * @param id
     * @param response
     * @throws IOException
     */

    @GetMapping("/download/{id}")
    public void download(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> byId = attachmentRepository.findById(id);
        if (byId.isPresent()) {
            Attachment attachment = byId.get();
            Optional<AttachmentContent> byAttachmentId = attachmentContentRepository.findByAttachmentId(id);
            if (byAttachmentId.isPresent()) {
                AttachmentContent attachmentContent = byAttachmentId.get();
                response.setContentType(attachment.getContentType());
                response.setHeader("Content-Disposition", attachment.getFileOriginalName() + "/:" + attachment.getSize());
                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());
            }
        }
    }

    /**
     * NAME ORQALI FILENI YUKLASH
     *
     * @param name
     * @param response
     * @throws IOException
     */

    @GetMapping("/downloadWithName")
    public void downloadWithName(@RequestBody String name, HttpServletResponse response) throws IOException {
        Optional<Attachment> byId = attachmentRepository.findByName(name);
        if (byId.isPresent()) {
            Attachment attachment = byId.get();
            Optional<AttachmentContent> byAttachmentId = attachmentContentRepository.findByAttachmentId(attachment.getId());
            if (byAttachmentId.isPresent()) {
                AttachmentContent attachmentContent = byAttachmentId.get();
                response.setContentType(attachment.getContentType());
                response.setHeader("Content-Disposition", attachment.getFileOriginalName() + "/:" + attachment.getSize());
                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());
            }
        }
    }

    /**
     * ID ORQALI RASM YOKI FILENI O'CHIRISH
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ApiResponse deleteMedia(@PathVariable Integer id) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
            attachmentContentRepository.deleteById(optionalAttachmentContent.get().getId());
            attachmentRepository.deleteById(attachment.getId());
            return new ApiResponse("DELETED", true);
        }
        return new ApiResponse("NOT FOUND", false);
    }

    /**
     * BIR NECHTA FILENI BIR VAQTDA DB GA SAQLASH
     *
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadAnyFile")
    public ApiResponse uploadAnyFiles(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        List<Attachment> attachmentList = new ArrayList<>();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                String originalFilename = file.getOriginalFilename();
                Attachment attachment = new Attachment();
                attachment.setName(originalFilename);
                attachment.setFileOriginalName(originalFilename);
                attachment.setSize(file.getSize());
                attachment.setContentType(file.getContentType());

                attachmentRepository.save(attachment);

                attachmentList.add(attachment);
                AttachmentContent attachmentContent = new AttachmentContent();
                attachmentContent.setMainContent(file.getBytes());
                attachmentContent.setAttachment(attachment);
                attachmentContentRepository.save(attachmentContent);
            } else {
                return new ApiResponse("NOT SAVED", false);
            }
        }
        return new ApiResponse("FILES SUCCESSFULLY SAVED", true, attachmentList);
    }


}
