package uz.uzkassa.smartposrestaurant.web.rest.errors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.*;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;
import tech.jhipster.config.JHipsterConstants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";

    private final Environment env;


    public ExceptionTranslator(Environment env) {
        this.env = env;
    }

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null) {
            return null;
        }
        Problem problem = entity.getBody();

        if (!HttpStatus.UNAUTHORIZED.equals(entity.getStatusCode())) {
            String path = request.getNativeRequest(HttpServletRequest.class).getRequestURI();
            String method = request.getNativeRequest(HttpServletRequest.class).getMethod();
            String user = null;
            if (request.toString().contains("user=")) {
                user = request.toString().split("user=")[1];
            }
        }

        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
        ProblemBuilder builder = Problem
            .builder()
            .withStatus(problem.getStatus())
            .withTitle(problem.getTitle())
            .with(PATH_KEY, requestUri);

        if (problem instanceof ConstraintViolationProblem) {
            builder
                .with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION);
        } else {
            builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail()).withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
                builder.with(MESSAGE_KEY, "error.http." + problem.getStatus().getStatusCode());
            }
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldErrorVm> fieldErrors = result
            .getFieldErrors()
            .stream()
            .map(f ->
                new FieldErrorVm(
                    f.getObjectName().replaceFirst("DTO$", ""),
                    f.getField(),
                    StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode()
                )
            )
            .collect(Collectors.toList());

        Problem problem = Problem
            .builder()
            //.withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
            .withTitle("Method argument not valid")
            .withStatus(defaultConstraintViolationStatus())
            .with(MESSAGE_KEY, ErrorConstants.ERR_VALIDATION)
            .with(FIELD_ERRORS_KEY, fieldErrors)
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Problem> handleNotFoundException(NotFoundException e, NativeWebRequest request) {
        Problem problem = Problem
            .builder()
            .withStatus(e.getStatus())
            .withTitle(e.getUserMessage())
            .withDetail(Optional.ofNullable(e.getDeveloperMessage()).orElse(e.getUserMessage()))
            .build();
        return create(e, problem, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Problem> handleBadRequestException(BadRequestException e, NativeWebRequest request) {
        Problem problem = Problem
            .builder()
            .withStatus(e.getStatus())
            .withTitle(e.getUserMessage().toString())
            .withDetail(Optional.ofNullable(e.getDeveloperMessage()).orElse(e.getUserMessage().toString()))
            .build();
        return create(e, problem, request);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<Problem> handleEntityAlreadyExistException(EntityAlreadyExistException e, NativeWebRequest request) {
        Problem problem = Problem
            .builder()
            .withStatus(e.getStatus())
            .withTitle(e.getUserMessage())
            .withDetail(Optional.ofNullable(e.getDeveloperMessage()).orElse(e.getUserMessage()))
            .build();
        return create(e, problem, request);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Problem> handleConflictException(ConflictException e, NativeWebRequest request) {
        Problem problem = Problem
            .builder()
            .withStatus(e.getStatus())
            .withTitle(e.getUserMessage())
            .withDetail(Optional.ofNullable(e.getDeveloperMessage()).orElse(e.getUserMessage()))
            .build();
        return create(e, problem, request);
    }

    @ExceptionHandler(NotModifiedException.class)
    public ResponseEntity<Problem> handleConflictException(NotModifiedException e, NativeWebRequest request) {
        Problem problem = Problem
            .builder()
            .withStatus(e.getStatus())
            .withTitle(e.getUserMessage())
            .withDetail(Optional.ofNullable(e.getDeveloperMessage()).orElse(e.getUserMessage()))
            .build();
        return create(e, problem, request);
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<Problem> handleIntegrationException(IntegrationException e, NativeWebRequest request) {
        if (e.getError() != null && StringUtils.isNotEmpty(e.getError().getMessage())) {
            Problem problem = Problem
                .builder()
                .withStatus(Status.valueOf(e.getError().getStatus()))
                .withTitle(e.getError().getMessage())
                .withDetail(e.getError().getMessage())
                .build();
            return create(e, problem, request);
        } else {
            Problem problem = Problem
                .builder()
                .withStatus(Status.SERVICE_UNAVAILABLE)
                .withTitle(e.getMessage())
                .withDetail(e.getMessage())
                .build();
            return create(e, problem, request);
        }
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.CONFLICT).with(MESSAGE_KEY, ErrorConstants.ERR_CONCURRENCY_FAILURE).build();
        return create(ex, problem, request);
    }

    @Override
    public ProblemBuilder prepare(final Throwable throwable, final StatusType status, final URI type) {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            if (throwable instanceof HttpMessageConversionException) {
                return Problem
                    .builder()
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unable to convert http message")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
            if (throwable instanceof DataAccessException) {
                return Problem
                    .builder()
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Failure during data access")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
            if (containsPackageName(throwable.getMessage())) {
                return Problem
                    .builder()
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unexpected runtime exception")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
        }

        return Problem
            .builder()
            .withTitle(status.getReasonPhrase())
            .withStatus(status)
            .withDetail(throwable.getMessage())
            .withCause(
                Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
            );
    }

    private boolean containsPackageName(String message) {
        // This list is for sure not complete
        return StringUtils.containsAny(message, "org.", "java.", "net.", "javax.", "com.", "io.", "de.", "uz.uzkassa.smartpossupply");
    }
}
