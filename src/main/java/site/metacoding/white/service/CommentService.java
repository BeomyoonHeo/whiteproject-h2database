package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.CommentRepository;
import site.metacoding.white.dto.CommentRequestDto.CommentSaveReqDto;
import site.metacoding.white.dto.CommentResponseDto.CommentSaveRespDto;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentSaveRespDto save(CommentSaveReqDto commentSaveReqDto) {

        // 1. Board가 있는지 확인
        Optional<Board> boardOP = boardRepository.findById(commentSaveReqDto.getBoardId());
        if (boardOP.isPresent()) {

            Comment comment = commentSaveReqDto.toEntity(boardOP.get());

            Comment commentPS = commentRepository.save(comment);

            CommentSaveRespDto commentSaveRespDto = new CommentSaveRespDto(commentPS);

            return commentSaveRespDto;
            
        } else {
            throw new RuntimeException("게시글이 없어 댓글을 쓸 수 없습니다.");
        }
        // 2. Comment 객체 만들기


        
    }

}
