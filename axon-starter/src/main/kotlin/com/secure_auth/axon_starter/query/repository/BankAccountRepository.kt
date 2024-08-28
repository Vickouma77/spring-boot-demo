package com.secure_auth.axon_starter.query.repository

import com.secure_auth.axon_starter.query.model.BankAccountProjection
import org.springframework.data.jpa.repository.JpaRepository
/**
 * Repository interface for managing [BankAccountProjection] entities.
 *
 * The [BankAccountRepository] extends [JpaRepository], providing standard CRUD
 * operations for the [BankAccountProjection] entity. This interface enables interaction
 * with the database to store, retrieve, update, and delete bank account projections.
 *
 * By extending [JpaRepository], this repository gains methods such as [findById],
 * {save}, and {delete} without requiring explicit implementation.
 *
 * @see BankAccountProjection
 * @since 1.0
 */

interface BankAccountRepository: JpaRepository<BankAccountProjection, String>
