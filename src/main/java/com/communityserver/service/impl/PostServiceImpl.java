package com.communityserver.service.impl;

import com.communityserver.dto.CommentDTO;
import com.communityserver.dto.FileDTO;
import com.communityserver.dto.PostDTO;
import com.communityserver.dto.RankPostDTO;
import com.communityserver.exception.*;
import com.communityserver.mapper.FileMapper;
import com.communityserver.mapper.PostMapper;
import com.communityserver.mapper.UserInfoMapper;
import com.communityserver.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final int DENIED_PERMISSION = 0;
    private final int FAIL_NUMBER = 0;
    private final PostMapper postMapper;
    private final UserInfoMapper userMapper;
    private final FileMapper fileMapper;
    private final Logger logger = LogManager.getLogger(PostServiceImpl.class);

    public PostServiceImpl(PostMapper postMapper, FileMapper fileMapper, UserInfoMapper userMapper){
        this.postMapper = postMapper;
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    @CacheEvict(value = "post", allEntries = true)
    @Override
    @Transactional
    public PostDTO addPost(PostDTO postDTO, int userNumber){
        if(postDTO.isAdminPost() && userMapper.adminUserCheck(userNumber)) {
            logger.warn("권한 부족");
            throw new PermissionDeniedException();
        }

        postDTO.setUserNumber(userNumber);
        postDTO.setCreateTime(new Date());

        if(postMapper.addPost(postDTO) != FAIL_NUMBER) {
            logger.info("게시글 "+ postDTO.getPostName() +"을 추가했습니다.");
            int postNumber = postDTO.getPostNumber();
            List<FileDTO> fileDTOS = postDTO.getFileDTOS();
            for (int i = 0; i < fileDTOS.size(); i++) {
                FileDTO fileDTO = fileDTOS.get(i);
                fileDTO.setPostNumber(postNumber);
                if(fileMapper.addFile(fileDTO) != FAIL_NUMBER) {
                    logger.info("게시글에 첨부파일 " + fileDTO.getFileName() + "을 추가했습니다.");
                }
                else{
                    logger.warn(fileDTO.getFileName() + "을 추가하지 못했습니다.");
                    throw new AddFailedException();
                }
            }
            PostDTO resultPostDTO = this.selectPost(postDTO.getPostNumber());;
            return resultPostDTO;
        }
        else{
            logger.warn("게시글 "+ postDTO.getPostName() +"을 추가하지 못했습니다.");
            throw new AddFailedException();
        }
    }

    @Override
    public void checkHasPermission(int loginUserNumber, int postNumber){
        if(postMapper.checkHasPermission(loginUserNumber, postNumber) == DENIED_PERMISSION) {
            logger.warn("게시글의 권한이 없습니다.");
            throw new PermissionDeniedException();
        }
    }

    @CacheEvict(value = "post", key = "#postNumber")
    @Override
    @Transactional
    public PostDTO updatePost(PostDTO postDTO, int postNumber){
        if(postMapper.updatePost(postDTO, postNumber) != FAIL_NUMBER) {
            logger.info("게시글 "+ postNumber +"을 수정했습니다.");

            PostDTO resultPostDTO = this.selectPost(postNumber);;
            return resultPostDTO;
        }
        else {
            logger.warn("게시글 "+ postNumber +"을 수정하지 못했습니다.");
            throw new UpdateFailedException();
        }
    }

    @Cacheable(value = "post", key = "#postNumber", unless="#result == null")
    @Override
    public PostDTO selectPost(int postNumber){
        Optional<PostDTO> optionalPostDTO = postMapper.selectPost(postNumber);;
        if (optionalPostDTO.isPresent()) {
            logger.info("게시글 "+ postNumber +"을 조회했습니다.");
            this.addView(postNumber);
            return optionalPostDTO.get();
        }
        else{
            logger.warn("게시글 "+ postNumber +"을 조회하지 못했습니다.");
            throw new NotMatchingException();
        }
    }

    @Override
    public List<RankPostDTO> selectRankPost(){
        List<RankPostDTO> rankingPostDTOS = postMapper.selectRankPost();
        if (rankingPostDTOS.isEmpty()) {
            logger.warn("게시글 랭킹을 조회하지 못했습니다.");
            throw new NotMatchingException();
        }
        else{
            logger.info("게시글 랭킹을 조회했습니다.");
            return rankingPostDTOS;
        }
    }

    @Override
    @Transactional
    public void deleteAllRankPost(){
        if(postMapper.deleteAllRankPost() != FAIL_NUMBER) {
            logger.info("게시글 랭킹을 삭제했습니다.");
        }
        else {
            logger.warn("게시글 랭킹을 삭제하지 못했습니다.");
            throw new DeletionFailedException();
        }
    }

    @Override
    @Transactional
    public void updateRank(){
        if(postMapper.updateRank() != FAIL_NUMBER) {
            logger.info("게시글 랭킹을 업데이트했습니다.");
        }
        else {
            logger.warn("게시글 랭킹을 업데이트하지 못했습니다.");
            throw new UpdateFailedException();
        }
    }

    @Override
    @Transactional
    public void addView(int postNumber){
        if(postMapper.addView(postNumber) != FAIL_NUMBER) {
            logger.info("게시글 "+ postNumber +"의 조회수을 증가했습니다.");
        }
        else {
            logger.warn("게시글 "+ postNumber +"을 조회수을 증가하지 못했습니다.");
            throw new UpdateFailedException();
        }
    }

    @Override
    @Transactional
    public List<CommentDTO> addComment(CommentDTO commentDTO, int postNumber, int loginUserNumber){
        if(postMapper.addComment(commentDTO, postNumber, loginUserNumber) != FAIL_NUMBER){
            logger.info("사용자 "+loginUserNumber + "가 게시글 "+ postNumber +"에 댓글을 추가했습니다.");
            List<CommentDTO> commentDTOS = postMapper.selectComment(postNumber);
            if(commentDTOS.isEmpty()){
                logger.warn("게시글 "+ postNumber + "의 댓글을 조회하지 못했습니다.");
                throw new NotMatchingException();
            }
            else{
                logger.info("게시글 "+ postNumber + "의 댓글을 조회했습니다.");
                return commentDTOS;
            }
        }
        else{
            logger.warn("사용자 "+loginUserNumber + "가 게시글 "+ postNumber +"에 댓글을 추가하지 못했습니다.");
            throw new AddFailedException();
        }
    }

    @CacheEvict(value = "post", key = "#postNumber")
    @Override
    @Transactional
    public void deletePost(int postNumber, int loginUserNumber){
        if(postMapper.deletePost(postNumber, loginUserNumber) != null){
            logger.info("게시글 " + postNumber + "을 삭제했습니다.");
        }
        else{
            logger.warn("게시글 " + postNumber + "을 삭제하지 못했습니다.");
            throw new DeletionFailedException();
        }
    }
}
