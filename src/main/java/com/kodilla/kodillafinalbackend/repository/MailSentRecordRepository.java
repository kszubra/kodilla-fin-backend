package com.kodilla.kodillafinalbackend.repository;

import com.kodilla.kodillafinalbackend.domain.MailSentRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MailSentRecordRepository extends CrudRepository<MailSentRecord, Long> {
    @Override
    MailSentRecord save(MailSentRecord record);

    @Override
    List<MailSentRecord> findAll();

    @Override
    void deleteAll();

}
