package site.metacoding.white.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.web.dto.request.BoardDto;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(BoardDto boardDto) {
        Board board = boardDto.toEntity(boardDto);
        boardRepository.save(board);
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id);
        board = boardDto.updateEntity(board);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public User findByAuthorForUser(String author) {
        return userRepository.findByUserName(author);
    }
}
