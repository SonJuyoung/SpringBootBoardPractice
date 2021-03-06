package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws IOException {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = java.util.UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);

        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    public Page<Board> boardList(Pageable pageable) {

        return  boardRepository.findAll(pageable);
    }

    public Page<Board> boardSearchList(String searchTxt, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchTxt, pageable);
    }

    public Board boardDetail(int id) {

        return boardRepository.findById(id).get();
    }

    public void boardDelete(int id) {
        boardRepository.deleteById(id);
    }
}
