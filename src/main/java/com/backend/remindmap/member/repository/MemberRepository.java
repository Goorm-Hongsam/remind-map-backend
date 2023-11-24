package com.backend.remindmap.member.repository;

import com.backend.remindmap.member.domain.KakaoToken.KakaoMemberToken;
import com.backend.remindmap.member.domain.Member.Member;
import com.backend.remindmap.member.domain.Member.MemberRefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findMemberById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public void saveRefreshToken(MemberRefreshToken memberRefreshToken) {
        em.persist(memberRefreshToken);
    }

    public Optional<MemberRefreshToken> findRefreshTokenByMemberId(Long id) {
        try {
            MemberRefreshToken result = em.createQuery("select token from MemberRefreshToken token where token.memberId = :memberId", MemberRefreshToken.class)
                    .setParameter("memberId", id)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void saveKakaoToken(KakaoMemberToken kakaoMemberToken) {
        em.persist(kakaoMemberToken);
    }

    public Optional<KakaoMemberToken> findKakaoMemberTokenByMemberId(Long id) {
        try {
            KakaoMemberToken result = em.createQuery("select token from KakaoMemberToken token where token.memberId = :memberId", KakaoMemberToken.class)
                    .setParameter("memberId", id)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
