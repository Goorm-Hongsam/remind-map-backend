package com.backend.remindmap.group.repository.groupMember;

import com.backend.remindmap.group.domain.groupMember.GroupMember;
import com.backend.remindmap.group.domain.groupMember.GroupMemberDto;
import com.backend.remindmap.group.repository.groupMember.GroupMemberRepository;
import com.backend.remindmap.member.domain.Member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class JpaGroupMemberRepository implements GroupMemberRepository {
    private final EntityManager em;

    @Override
    public GroupMember addMember(GroupMemberDto groupMemberDto) {
        GroupMember groupMember = new GroupMember(groupMemberDto.getGroupId(), groupMemberDto.getMemberId());
        em.persist(groupMember);
        return groupMember;
    }

    @Override
    public void removeMember(GroupMemberDto groupMemberDto) {
        String jpql = "delete from GroupMember gm where gm.memberId = :memberId and gm.groupId = :groupId";
        em.createQuery(jpql)
                .setParameter("memberId", groupMemberDto.getMemberId())
                .setParameter("groupId", groupMemberDto.getGroupId())
                .executeUpdate();
    }

    @Override
    public List<Member> findAllMember(Long groupId) {
        String jpql = "select m from Member m " +
                "join GroupMember gm on gm.memberId = m.memberId " +
                "where gm.groupId = :groupId";
        List<Member> result = em.createQuery(jpql, Member.class)
                .setParameter("groupId", groupId)
                .getResultList();
        return result;
    }
}
