//package com.anjiplus.gaea.push.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.*;
//
//@Slf4j
//public class ExchangeClient {
//
//
//    private final String hostname;
//    private final ExchangeVersion exchangeVersion;
//    private final String domain;
//    private final String username;
//    private final String password;
//    private final String subject;
//    private final List<String> recipientTo;
//    private final List<String> recipientCc;
//    private final List<String> recipientBcc;
//    private final Map<String, MultipartFile> attachments;
//    private final String message;
//
//    private ExchangeClient(ExchangeClientBuilder builder) {
//        this.hostname = builder.hostname;
//        this.exchangeVersion = builder.exchangeVersion;
//        this.domain = builder.domain;
//        this.username = builder.username;
//        this.password = builder.password;
//        this.subject = builder.subject;
//        this.recipientTo = builder.recipientTo;
//        this.recipientCc = builder.recipientCc;
//        this.recipientBcc = builder.recipientBcc;
//        this.attachments = builder.attachments;
//        this.message = builder.message;
//    }
//
//    public static class ExchangeClientBuilder {
//
//        private String hostname;
//        private ExchangeVersion exchangeVersion;
//        private String domain;
//        private String username;
//        private String password;
//        private String subject;
//        private List<String> recipientTo;
//        private List<String> recipientCc;
//        private List<String> recipientBcc;
//        private Map<String, MultipartFile> attachments;
//        private String message;
//
//        public ExchangeClientBuilder() {
//            this.exchangeVersion = ExchangeVersion.Exchange2010_SP1;
//            this.hostname = "";
//            this.username = "";
//            this.password = "";
//            this.subject = "";
//            this.recipientTo = new ArrayList<>(0);
//            this.recipientCc = new ArrayList<>(0);
//            this.recipientBcc = new ArrayList<>(0);
//            this.attachments = new HashMap<>();
//            this.message = "";
//        }
//
//        /**
//         * The hostname of the Exchange Web Service. It will be used for
//         * connecting with URI https://hostname/ews/exchange.asmx
//         *
//         * @param hostname the hostname of the MS Exchange Smtp Server.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder hostname(String hostname) {
//            this.hostname = hostname;
//            return this;
//        }
//
//        /**
//         * The Exchange Web Server version.
//         *
//         * @param exchangeVersion the Exchange Web Server version.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder exchangeVersion(ExchangeVersion exchangeVersion) {
//            this.exchangeVersion = exchangeVersion;
//            return this;
//        }
//
//        /**
//         * The domain of the MS Exchange Smtp Server.
//         *
//         * @param domain the domain of the Active Directory. The first part of
//         *               the username. For example: MYDOMAIN\\username, set the MYDOMAIN.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder domain(String domain) {
//            this.domain = domain;
//            return this;
//        }
//
//        /**
//         * The username of the MS Exchange Smtp Server. The second part of the
//         * username. For example: MYDOMAIN\\username, set the username.
//         *
//         * @param username the username of the MS Exchange Smtp Server.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder username(String username) {
//            this.username = username;
//            return this;
//        }
//
//        /**
//         * The password of the MS Exchange Smtp Server.
//         *
//         * @param password the password of the MS Exchange Smtp Server.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder password(String password) {
//            this.password = password;
//            return this;
//        }
//
//        /**
//         * The subject for this send.
//         *
//         * @param subject the subject for this send.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder subject(String subject) {
//            this.subject = subject;
//            return this;
//        }
//
//        /**
//         * The recipient for this send.
//         *
//         * @param recipientTo the recipient for this send.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder recipientTo(String[] recipientTo) {
//            this.recipientTo = Arrays.asList(recipientTo);
//            return this;
//        }
//
//        public ExchangeClientBuilder recipientTo(List<String> recipientTo) {
//            this.recipientTo = recipientTo;
//            return this;
//        }
//
//        public ExchangeClientBuilder recipientTo(String recipientTo) {
//            if (recipientTo.contains(";")) {
//                this.recipientTo = Arrays.asList(recipientTo.split(";"));
//            } else {
//                this.recipientTo = Arrays.asList(recipientTo.split(","));
//            }
//            return this;
//        }
//
//        /**
//         * You can specify one or more email address that will be used as cc
//         * recipients.
//         *
//         * @param recipientCc  the first cc email address.
//         * @param recipientsCc the other cc email address for this send.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder recipientCc(String recipientCc, String... recipientsCc) {
//            // Prepare the list.
//            List<String> recipients = new ArrayList<>(1 + recipientsCc.length);
//            recipients.add(recipientCc);
//            recipients.addAll(Arrays.asList(recipientsCc));
//            // Set the list.
//            this.recipientCc = recipients;
//            return this;
//        }
//
//        public ExchangeClientBuilder recipientCc(String[] recipientCc) {
//            // Set the list.
//            this.recipientCc = Arrays.asList(recipientCc);
//            return this;
//        }
//
//        public ExchangeClientBuilder recipientCc(String recipientCc) {
//            if (StringUtils.isNotEmpty(recipientCc)) {
//                if (recipientCc.contains(";")) {
//                    this.recipientCc = Arrays.asList(recipientCc.split(";"));
//                } else {
//                    this.recipientCc = Arrays.asList(recipientCc.split(","));
//                }
//            }
//            return this;
//        }
//
//        /**
//         * You can specify a list with email addresses that will be used as cc
//         * for this email send.
//         *
//         * @param recipientCc the list with email addresses that will be used as
//         *                    cc for this email send.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder recipientCc(List<String> recipientCc) {
//            this.recipientCc = recipientCc;
//            return this;
//        }
//
//        /**
//         * You can specify one or more email address that will be used as bcc
//         * recipients.
//         *
//         * @param recipientBcc  the first bcc email address.
//         * @param recipientsBcc the other bcc email address for this send.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder recipientBcc(String recipientBcc, String... recipientsBcc) {
//            // Prepare the list.
//            List<String> recipients = new ArrayList<>(1 + recipientsBcc.length);
//            recipients.add(recipientBcc);
//            recipients.addAll(Arrays.asList(recipientsBcc));
//            // Set the list.
//            this.recipientBcc = recipients;
//            return this;
//        }
//
//        public ExchangeClientBuilder recipientBcc(String[] recipientBcc) {
//            // Set the list.
//            this.recipientBcc = Arrays.asList(recipientBcc);
//            return this;
//        }
//
//        public ExchangeClientBuilder recipientBcc(String recipientBcc) {
//            if (StringUtils.isNotEmpty(recipientBcc)) {
//                if (recipientBcc.contains(";")) {
//                    this.recipientBcc = Arrays.asList(recipientBcc.split(";"));
//                } else {
//                    this.recipientBcc = Arrays.asList(recipientBcc.split(","));
//                }
//            }
//            return this;
//        }
//
//        /**
//         * You can specify a list with email addresses that will be used as bcc
//         * for this email send.
//         *
//         * @param recipientBcc the list with email addresses that will be used
//         *                     as bcc for this email send.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder recipientBcc(List<String> recipientBcc) {
//            this.recipientBcc = recipientBcc;
//            return this;
//        }
//
//        /**
//         * You can specify one or more email address that will be used as cc
//         * recipients.
//         *
//         * @param attachment the first attachment.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder attachments(Map<String, MultipartFile> attachment) {
//            // Set the list.
//            this.attachments = attachment;
//            return this;
//        }
//
//
//        /**
//         * The body of the email message.
//         *
//         * @param message the body of the email message.
//         * @return the builder for chain usage.
//         */
//        public ExchangeClientBuilder message(String message) {
//            this.message = message;
//            return this;
//        }
//
//        /**
//         * Build a mail.
//         *
//         * @return an EmailApacheUtils object.
//         */
//        public ExchangeClient build() {
//            return new ExchangeClient(this);
//        }
//    }
//
//    public boolean sendExchange() throws Exception {
//        // The Exchange Server Version.
//        ExchangeService exchangeService = new ExchangeService(exchangeVersion);
//
//        // Credentials to sign in the MS Exchange Server.
//        ExchangeCredentials exchangeCredentials = new WebCredentials(username, password, domain);
//        exchangeService.setCredentials(exchangeCredentials);
//
//        // URL of exchange web service for the mailbox.
//        try {
//            exchangeService.setUrl(new URI("https://" + hostname + "/ews/Exchange.asmx"));
//        } catch (URISyntaxException ex) {
//            log.error("An exception occured while creating the uri for exchange service.", ex);
//            throw new Exception(ex);
//        }
//
//        // The email.
//        EmailMessage emailMessage;
//        try {
//            emailMessage = new EmailMessage(exchangeService);
//            emailMessage.setSubject(subject);
//            emailMessage.setBody(MessageBody.getMessageBodyFromText(message));
//        } catch (Exception ex) {
//            log.error("An exception occured while setting the email message.", ex);
//            throw new Exception(ex);
//        }
//
//        // TO recipient.
//        if (null != recipientTo && recipientTo.size() > 0) {
//            for (String recipient : recipientTo) {
//                try {
//                    emailMessage.getToRecipients().add(recipient);
//                } catch (ServiceLocalException ex) {
//                    log.error("An exception occured while sstting the TO recipient(" + recipient + ").", ex);
//                    throw new Exception(ex);
//                }
//            }
//        }
//
//
//        // CC recipient.
//        if (null != recipientCc && recipientCc.size() > 0) {
//            for (String recipient : recipientCc) {
//                try {
//                    if (StringUtils.isNotEmpty(recipient)) {
//                        emailMessage.getCcRecipients().add(recipient);
//                    }
//                } catch (ServiceLocalException ex) {
//                    log.error("An exception occured while sstting the CC recipient(" + recipient + ").", ex);
//                    throw new Exception(ex);
//                }
//            }
//        }
//
//        // BCC recipient
//        if (null != recipientBcc && recipientBcc.size() > 0) {
//            for (String recipient : recipientBcc) {
//                try {
//                    if (StringUtils.isNotEmpty(recipient)) {
//                        emailMessage.getBccRecipients().add(recipient);
//                    }
//                } catch (ServiceLocalException ex) {
//                    log.error("An exception occured while sstting the BCC recipient(" + recipient + ").", ex);
//                    throw new Exception(ex);
//                }
//            }
//        }
//
//        // Attachements.
//        if (attachments != null && attachments.size() > 0) {
//            for (Map.Entry<String, MultipartFile> fileEntry : attachments.entrySet()) {
//                try {
//                    emailMessage.getAttachments().addFileAttachment(fileEntry.getKey(), fileEntry.getValue().getBytes());
//                } catch (Exception ex) {
//                    log.error("An exception occured while setting the attachment.", ex);
//                    throw new Exception(ex);
//                }
//            }
//        }
//
//        try {
//            emailMessage.send();
//            log.info("An email is send to:{} success.", JSONObject.toJSONString(recipientTo));
//        } catch (Exception ex) {
//            log.error("An exception occured while sending an email.", ex);
//            throw new Exception(ex);
//        }
//
//        return true;
//    }
//
//}
