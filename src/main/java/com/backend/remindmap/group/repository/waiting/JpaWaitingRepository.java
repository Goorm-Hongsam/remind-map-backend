package com.backend.remindmap.group.repository.waiting;

import com.backend.remindmap.group.domain.groupMember.GroupMemberDto;
import com.backend.remindmap.group.domain.waiting.InvitedMemberDto;
import com.backend.remindmap.group.domain.waiting.Waiting;
import com.backend.remindmap.group.domain.waiting.WaitingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaWaitingRepository implements WaitingRepository{

    private final EntityManager em;

    @Override
    public void save(List<InvitedMemberDto> list) {
        for(InvitedMemberDto info : list) {
            Waiting waiting = new Waiting(info.getGroupId(), info.getMemberId(), info.getLeaderId());
            em.persist(waiting);
        }
    }

    @Override
    public void remove(GroupMemberDto groupMemberDto) {
        String jpql = "delete from Waiting w where w.memberId = :memberId and w.groupId = :groupId";
        em.createQuery(jpql)
                .setParameter("memberId", groupMemberDto.getMemberId())
                .setParameter("groupId", groupMemberDto.getGroupId())
                .executeUpdate();
    }

    @Override
    public List<WaitingResponse> findAll(Long memberId) {
        String jpql = "select new com.backend.remindmap.group.domain.WaitingResponse(m.nickname, g.title, w.groupId, w.memberId, w.leaderId) " +
                "from Waiting w " +
                "join Group g on w.groupId = g.groupId " +
                "join Member m on w.leaderId = m.memberId" +
                " where w.memberId = :memberId";
        List<WaitingResponse> list = em.createQuery(jpql, WaitingResponse.class)
                .setParameter("memberId", memberId)
                .getResultList();
        return list;
    }
}
