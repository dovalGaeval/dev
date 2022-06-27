package com.dovalgaeval.dev.repository;

import com.dovalgaeval.dev.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
