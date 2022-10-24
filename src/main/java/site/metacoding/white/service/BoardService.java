package site.metacoding.white.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;

// 트랜잭션 관리
// DTO 변환해서 컨트롤러에게 돌려줘야함

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    
    @Transactional // 무조건 명시해줘야 한다.
    public void save(Board board) {
        boardRepository.save(board);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public void update(Long id, Board board) {
        Board boardPS = boardRepository.findById(id);
        boardPS.setTitle(board.getTitle());
        boardPS.setContent(board.getContent());

    } // 트랜잭션 종료시 -> 더티체킹을 함

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

}
