package com.samia.ecole.repositories;

import com.samia.ecole.entities.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;
@Repository
public interface JwtRepository extends JpaRepository<Jwt, Long> {
   Optional<Jwt> findByValeur(String valeur);   //  <== boolean desactive, boolean expire

   //Optional<Jwt> findByValeurDesactiveExpire(String valeur, boolean desactive, boolean expire);
   @Query("FROM Jwt j WHERE j.desactive = :desactive AND j.expire = :expire AND j.user.email = :email")
   Optional<Jwt> findUserValidToken(String email, boolean desactive, boolean expire);

   @Query("FROM Jwt j WHERE j.user.email = :email")
   Stream<Jwt> findByEmail(String email);

   @Query("FROM Jwt j WHERE j.refreshToken.valeur = :valeur")
   Optional<Jwt> findByRefreshToken(String valeur);

   void deleteAllByExpireAndDesactive(boolean expire, boolean desactive);
}
