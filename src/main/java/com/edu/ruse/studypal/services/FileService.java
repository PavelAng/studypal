package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.FilesController;
import com.edu.ruse.studypal.dtos.FileGetDto;
import com.edu.ruse.studypal.dtos.FileGetSlimDto;
import com.edu.ruse.studypal.dtos.FilePostDto;
import com.edu.ruse.studypal.entities.File;
import com.edu.ruse.studypal.mappers.FileMapper;
import com.edu.ruse.studypal.repositories.FileRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private static final int PAGE_SIZE = 2;
    private static final Logger LOGGER = LogManager.getLogger(FilesController.class);

    @Autowired
    public FileService(FileRepository fileRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    public List<File> findAll() {
        return fileRepository.findAll();
    }

    public void save(File file) {
        fileRepository.save(file);
    }

    public void uploadFiles(List<File> files) {
        for (File file : files) {
            save(file);
        }
    }

    public Optional<File> findById(long id) {
        return fileRepository.findById(id);
    }

    public List<File> findByFileName(String filename) {
        return fileRepository.findByFileName(filename);
    }

    public void delete(File file) {
        fileRepository.delete(file);
    }

    public List<FileGetSlimDto> getAllFiles(int page) {
        Pageable pageable = Pageable.ofSize(PAGE_SIZE).withPage(page);
        Page<File> all = fileRepository.findAll(pageable);
        List<FileGetSlimDto> body = all.getContent().stream().map(fileMapper::toSlimDto).collect(Collectors.toList());

        return body;
    }

    public FileGetDto createFile(FilePostDto filePostDto) {
        File file = fileMapper.toEntityFromPostDto(filePostDto);
        file.setFileId((long) (fileRepository.findAll().size() + 1));
        File res = fileRepository.save(file);
        return fileMapper.toDto(res);
    }
}
