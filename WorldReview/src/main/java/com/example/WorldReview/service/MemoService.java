package com.example.WorldReview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.WorldReview.dto.MemoDTO;
import com.example.WorldReview.repository.MemoRepository;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public List<MemoDTO> getUserMemos(int userId) {
        return memoRepository.findByUserId(userId);
    }

    public MemoDTO getMemo(int id) {
        return memoRepository.findById(id);
    }

    public void createMemo(MemoDTO memo) {
        memoRepository.save(memo);
    }

    public void updateMemo(MemoDTO memo) {
        memoRepository.update(memo);
    }

    public void deleteMemo(int id) {
        memoRepository.delete(id);
    }
}
