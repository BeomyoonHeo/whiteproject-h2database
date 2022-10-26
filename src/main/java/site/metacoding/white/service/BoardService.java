package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.dto.BoardRequestDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardResponseDto.BoardDetailRespDto;
import site.metacoding.white.dto.BoardResponseDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardResponseDto.FindAllDto;

// 트랜잭션 관리
// DTO 변환해서 컨트롤러에게 돌려줘야함

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    
    @Transactional // 무조건 명시해줘야 한다.
    public BoardSaveRespDto save(BoardSaveReqDto boardSaveReqDto) {

        Board boardPS = boardRepository.save(boardSaveReqDto.toEntity());
    
        BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(boardPS);

        return boardSaveRespDto;
    }

    @Transactional(readOnly = true)
    public BoardDetailRespDto findById(Long id) {

        Optional<Board> boardOP = boardRepository.findById(id);

        if (boardOP.isPresent()) {
            return new BoardDetailRespDto(boardOP.get());
        } else {
            throw new RuntimeException("해당" + id + "로 상세보기를 할 수 없습니다.");
        }
    }

    @Transactional
    public void update(Long id, Board board) {

        Optional<Board> boardOP = boardRepository.findById(id);
        
        if (boardOP.isEmpty()) {
            throw new RuntimeException("해당" + id + "로 수정을 할 수 없습니다.");
        }
        boardOP.get().update(board.getTitle(), board.getContent());

    } // 트랜잭션 종료시 -> 더티체킹을 함

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public FindAllDto findAll() {
        return new FindAllDto(boardRepository.findAll());
    }

}
