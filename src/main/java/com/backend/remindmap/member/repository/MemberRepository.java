package com.backend.remindmap.member.repository;

import com.backend.remindmap.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findMemberById(Long id) {

        List<Member> users = em.createQuery("select m from Member m where m.memberId = :memberId", Member.class)
                .setParameter("memberId", id)
                .getResultList();

        return users.stream().findAny();

    }


}
