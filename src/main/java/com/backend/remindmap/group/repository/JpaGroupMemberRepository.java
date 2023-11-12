package com.remind.map.group.repository;

import com.remind.map.group.domain.GroupMember;
import com.remind.map.group.domain.GroupMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
    public void removeMember(Long id) {
        GroupMember groupMember = em.find(GroupMember.class, id);
        em.remove(groupMember);
    }
}
