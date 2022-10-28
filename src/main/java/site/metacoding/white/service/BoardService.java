package site.metacoding.white.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.dto.BoardRequestDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardRequestDto.BoardUpdateReqDto;
import site.metacoding.white.dto.BoardResponseDto.BoardAllRespDto;
import site.metacoding.white.dto.BoardResponseDto.BoardDetailRespDto;
import site.metacoding.white.dto.BoardResponseDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardResponseDto.BoardUpdateRespDto;

// 트랜잭션 관리
// DTO 변환해서 컨트롤러에게 돌려줘야함

@RequiredArgsConstructor
@Service
@ToString
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
    public BoardUpdateRespDto update(BoardUpdateReqDto boardUpdateReqDto) {

        Long id = boardUpdateReqDto.getId();

        Optional<Board> boardOP = boardRepository.findById(id);

        if (boardOP.isEmpty()) {
            
            throw new RuntimeException("해당" + id + "로 수정을 할 수 없습니다.");
        }
        boardOP.get().update(boardUpdateReqDto.getTitle(), boardUpdateReqDto.getContent());
        Board boardPS = boardOP.get();
        return new BoardUpdateRespDto(boardPS);

    } // 트랜잭션 종료시 -> 더티체킹을 함

    @Transactional
    public void deleteById(Long id) {
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isEmpty()) {
            throw new RuntimeException("해당 번호" + id + "번 으로 삭제를 할 수 없습니다.");
        }
        boardRepository.deleteById(id);
    }

    @Transactional
    public List<BoardAllRespDto> findAll() {

        List<Board> boardPSs = boardRepository.findAll();
        List<BoardAllRespDto> boardAllRespDtos = new ArrayList<>();
        // 1. List의 크기만큼 for문 돌리기
        boardAllRespDtos = boardPSs.stream().map((board) -> new BoardAllRespDto(board)).collect(Collectors.toList());
        // 2. Board -> DTO로 옮겨야 함

        // 3. DTO를 LIST에 담기
        return boardAllRespDtos;
    }

}
