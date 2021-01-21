/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.charlescd.moove.infrastructure.configuration

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import feign.Response
import feign.codec.ErrorDecoder
import io.charlescd.moove.domain.exceptions.ClientException
import java.io.IOException
import java.lang.Exception
import java.nio.charset.StandardCharsets
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StreamUtils

@Configuration
class MatcherEncoderConfiguration {

    @Bean
    fun matcherErrorDecoder(): ErrorDecoder {
        return MatcherErrorDecoder()
    }

    class MatcherErrorDecoder : ErrorDecoder {
        private val logger = LoggerFactory.getLogger(this.javaClass)
        override fun decode(methodKey: String?, response: Response?): Exception {
            val responseMessage: ErrorResponse = this.extractMessageFromResponse(response)
            logger.info("Response as object $responseMessage")
            return ClientException(responseMessage.id, responseMessage.links, responseMessage.title, responseMessage.details,responseMessage.status,responseMessage.source, responseMessage.meta)
        }

        private fun extractMessageFromResponse(response: Response?): ErrorResponse {
            var responseAsString: String? = null
            try {
                responseAsString = response?.body()?.let {
                    StreamUtils.copyToString(it.asInputStream(), StandardCharsets.UTF_8)
                }
                logger.info("Response as object $responseAsString")
                return responseAsString?.let {
                    getResponseAsObject(it)
                } ?: ErrorResponse()

            } catch (ex: IOException) {
                logger.error(ex.message, ex)
                return ErrorResponse()
            }
        }

        private fun getResponseAsObject(message: String): ErrorResponse {
            return jacksonObjectMapper().readValue(message, ErrorResponse::class.java)
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class ErrorResponse(
        val id: String? = null,
        val links: List<String>? = null,
        val title: String? = null,
        val details: String? = null,
        val status: String? = null,
        val source: Map<String, String >? = null,
        val meta: String? = null
    )
}
