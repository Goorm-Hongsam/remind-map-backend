package com.backend.remindmap.group.repository.group;

import com.backend.remindmap.group.domain.group.Group;
import com.backend.remindmap.group.domain.group.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class JpaGroupRepository implements GroupRepository{
    private final EntityManager em;

    @Override
    public Group saveGroup(GroupDto groupDto) {
        Group group = new Group(groupDto.getTitle());
        em.persist(group);
        return group;
    }

    @Override
    public void updateGroup(Long id, GroupDto groupDto) {
        Group findedGroup = em.find(Group.class, id);
        findedGroup.setTitle(groupDto.getTitle());
    }

    @Override
    public Group removeGroup(Long id) {
        Group findedgroup = em.find(Group.class, id);
        em.remove(findedgroup);
        return findedgroup;
    }

    @Override
    public Optional<Group> findGroupById(Long id) {
        Group findedGroup = em.find(Group.class, id);
        return Optional.ofNullable(findedGroup);
    }

    @Override
    public List<Group> findAllGroup(Long id) {
        String jpql = "select g from Group g join GroupMember gm on g.groupId = gm.groupId where gm.memberId = :id";

        List<Group> resultList = em.createQuery(jpql, Group.class)
                .setParameter("id", id)
                .getResultList();
        return resultList;
    }
}
