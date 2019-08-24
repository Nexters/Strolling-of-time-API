//package com.nexters.wiw.api.web;
//
//
//import com.nexters.wiw.api.domain.GroupNotice;
//import com.nexters.wiw.api.service.GroupNoticeService;
//import com.nexters.wiw.api.ui.GroupNoticeRequestDto;
//import com.nexters.wiw.api.ui.GroupNoticeResponseDto;
//import com.nexters.wiw.api.ui.GroupResponseDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/")
//public class GroupNoticeController {
//
//    @Autowired
//    GroupNoticeService groupNoticeService;
//
//    @PostMapping("group/{id}/group-notice")
//    public ResponseEntity<GroupNoticeResponseDto> create(@RequestHeader("Authorization") String authHeader,
//                                              @PathVariable Long id,
//                                              @RequestBody @Valid GroupNoticeRequestDto groupNoticeRequestDto){
//        GroupNotice groupNotice = groupNoticeService.save(authHeader, id, groupNoticeRequestDto);
//        return new ResponseEntity<GroupNoticeResponseDto>(GroupNoticeResponseDto.of(groupNotice), HttpStatus.CREATED);
//    }
//
//    @PatchMapping("group-notice/{id}")
//    public ResponseEntity<GroupNoticeResponseDto> update(@PathVariable Long id,
//                                              @RequestBody @Valid GroupNoticeRequestDto groupNoticeRequestDto) {
//        GroupNotice updated = groupNoticeService.update(id, groupNoticeRequestDto);
//        return new ResponseEntity<GroupNoticeResponseDto>(GroupNoticeResponseDto.of(updated), HttpStatus.OK);
//    }
//
//    @DeleteMapping("group-notice/{id}")
//    public ResponseEntity<GroupNoticeResponseDto> delete(@PathVariable Long id) {
//        groupNoticeService.delete(id);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("group-notice/{id}")
//    public ResponseEntity<GroupNoticeResponseDto> getGroupNotice(@PathVariable Long id) {
//        GroupNoticeResponseDto result = GroupNoticeResponseDto.of(groupNoticeService.getGroupNoticeById(id));
//
//        return new ResponseEntity<GroupNoticeResponseDto>(result, HttpStatus.OK);
//    }
//
//    @GetMapping("group/{id}/notices")
//    public ResponseEntity<List<GroupNoticeResponseDto>> getGroupNoticeByGroupId(@PathVariable Long id,
//                                                                     @RequestParam(value = "keyword", required = false)
//                                                                             String keyword) {
//        List<GroupNoticeResponseDto> result = new ArrayList<>();
//
//        if(keyword != null) {
//            for(GroupNotice groupNotice : groupNoticeService.getGroupNoticeByTitle(keyword)){
//                result.add(GroupNoticeResponseDto.of(groupNotice));
//            }
//        } else {
//            for(GroupNotice groupNotice : groupNoticeService.getGroupByGroupId(id)){
//                result.add(GroupNoticeResponseDto.of(groupNotice));
//            }
//        }
//
//        if(result.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<List<GroupNoticeResponseDto>>(result, HttpStatus.OK);
//    }
//
//    @GetMapping("user/{id}/notices")
//    public ResponseEntity<List<GroupNoticeResponseDto>> getGroupNoticeByUserId(@PathVariable Long id) {
//        List<GroupNoticeResponseDto> result = new ArrayList<>();
//
//        for(GroupNotice groupNotice : groupNoticeService.getGroupByUserId(id)){
//            result.add(GroupNoticeResponseDto.of(groupNotice));
//        }
//
//        if(result.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<List<GroupNoticeResponseDto>>(result, HttpStatus.OK);
//    }
//}
