package com.example.dumpdata.repository;

import com.example.dumpdata.entity.MyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<MyRecord,Long> {

}
