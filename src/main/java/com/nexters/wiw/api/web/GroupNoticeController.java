package com.nexters.wiw.api.web;


import com.nexters.wiw.api.domain.GroupNotice;
import com.nexters.wiw.api.service.GroupNoticeService;
import com.nexters.wiw.api.ui.GroupNoticeRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class GroupNoticeController {

    @Autowired
    GroupNoticeService groupNoticeService;

    @PostMapping("group-notice")
    public ResponseEntity<GroupNotice> create(@RequestBody @Valid GroupNoticeRequestDto groupNoticeRequestDto){
        GroupNotice groupNotice = groupNoticeService.save(groupNoticeRequestDto);
        return new ResponseEntity<GroupNotice>(groupNotice, HttpStatus.CREATED);
    }

    @PatchMapping("group-notice/{id}")
    public ResponseEntity<GroupNotice> update(@PathVariable Long id,
                                              @RequestBody @Valid GroupNoticeRequestDto groupNoticeRequestDto) {
        GroupNotice updated = groupNoticeService.update(id, groupNoticeRequestDto);
        return new ResponseEntity<GroupNotice>(updated, HttpStatus.OK);
    }

    @DeleteMapping("group-notice/{id}")
    public ResponseEntity<GroupNotice> delete(@PathVariable Long id) {
        groupNoticeService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("group-notice/{id}")
    public ResponseEntity<GroupNotice> getGroupNotice(@PathVariable Long id) {
        GroupNotice groupNotice = groupNoticeService.getGroupNoticeById(id);
        return new ResponseEntity<GroupNotice>(groupNotice, HttpStatus.OK);
    }

    @GetMapping("group/{id}/notices")
    public ResponseEntity<List<GroupNotice>> getGroupNoticeByGroupId(@PathVariable Long id,
                                                                     @RequestParam(value = "keyword", required = false)
                                                                             String keyword) {
        List<GroupNotice> result;

        if(keyword != null) {
            result = groupNoticeService.getGroupNoticeByTitle(keyword);
        } else {
            result = groupNoticeService.getGroupByGroupId(id);
        }

        if(result.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<GroupNotice>>(result, HttpStatus.OK);
    }

//    @GetMapping("user/{id}/notices")
//    public ResponseEntity<List<GroupNotice>> getGroupNoticeByUserId(@PathVariable Long id) {
//        List<GroupNotice> result;
//
//        result = groupNoticeService.getGroupByUserId(id);
//
//        if(result.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<List<GroupNotice>>(result, HttpStatus.OK);
//    }
}
