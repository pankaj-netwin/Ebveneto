package com.ebveneto.webservices;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by Asmita on 04-01-2017.
 */

public interface NetworkAPI {
    //Login Screen
    @Multipart
    @POST("/doLogin")
    void doLogin(@Part("email") TypedString api,
                 @Part("password") TypedString password,
                 @Part("action") TypedString action,
                 @Part("is_mobile_request") TypedString is_mobile_request,
                 Callback<JsonObject> response);

    //Add services Screen
    @Multipart
    @POST("/getServices")
    void getAllServices(@Part("action") TypedString action,
                        @Part("tabella") TypedString tabella,
                        @Part("user_id") TypedString user_id,
                        @Part("is_mobile_request") TypedString is_mobile_request,
                        Callback<JsonObject> response);

    //Service details
    @Multipart
    @POST("/getServiceDetails")
    void getServicesDetails(@Part("action") TypedString action,
                            @Part("tabella") TypedString tabella,
                            @Part("user_id") TypedString user_id,
                            @Part("is_mobile_request") TypedString is_mobile_request,
                            @Part("service_id") TypedString service_id,
                            Callback<JsonObject> response);

    //Save service request
    @Multipart
    @POST("/SaveServiceRequest")
    void saveServicesDetails(@Part("tabella") TypedString tabella,
                             @Part("action") TypedString action,
                             @Part("idDIP") TypedString idDIP,
                             @Part("campiSezD") TypedString campiSezD,//static and mandatory
                             @Part("bpAnnoMese") TypedString bpAnnoMese,
                             @Part("testiSezD") TypedString testiSezD,//static and mandatory
                             @Part("allegati") TypedString allegati,//static and mandatory
                             @Part("IBAN") TypedString IBAN,
                             @Part("IDServizio") TypedString IDServizio,
                             @Part("privacy1") TypedString privacy1,
                             @Part("privacy2") TypedString privacy2,
                             @Part("idBenef2") TypedString company_id,
                             @Part("is_mobile_request") TypedString is_mobile_request,
                             @Part("bpFile") TypedFile bpFile,
                             @Part("stato_di_famiglia_o_certificato_di_paternitmaternit") TypedFile stato_di_famiglia_o_certificato_di_paternitmaternit,
                             @Part("rimborso_1") TypedFile rimborso_1,
                             @Part("rimborso_2") TypedFile rimborso_2,
                             @Part("rimborso_3") TypedFile rimborso_3,
                             @Part("rimborso_4") TypedFile rimborso_4,
                             @Part("rimborso_5") TypedFile rimborso_5,
                             @Part("rimborso_6") TypedFile rimborso_6,
                             @Part("rimborso_7") TypedFile rimborso_7,
                             @Part("rimborso_8") TypedFile rimborso_8,
                             @Part("rimborso_9") TypedFile rimborso_9,
                             @Part("rimborso_10") TypedFile rimborso10,
                             @Part("countAz") TypedString countAz,
                             @Part("parente") TypedString parente,
                             @Part("tipoParente") TypedString tipoParente,
                             @Part("cognomeParente") TypedString cognomeParente,
                             @Part("nomeParente") TypedString nomeParente,
                             @Part("luogoNascitaParente") TypedString luogoNascitaParente,
                             @Part("dtNascitaParente") TypedString dtNascitaParente,
                             @Part("certificato_medico_pediatra_o_ospedale") TypedFile certificato_medico_pediatra_o_ospedale,
                             @Part("attestazione_assenza_da_lavoro") TypedFile attestazione_assenza_da_lavoro,
                             @Part("cfParente") TypedString cfParente,
                             @Part("ProvNascita") TypedString ProvNascita,
                             @Part("lista_dipendenti") TypedFile lista_dipendenti,
                             @Part("stato_di_famiglia") TypedFile stato_di_famiglia,
                             @Part("ciFile") TypedFile ciFile,
                             @Part("ciScadenza") TypedString ciScadenza,
                             @Part("VisuraFile") TypedFile VisuraFile,
                             @Part("visuraData") TypedString visuraData,
                             @Part("stato_di_famiglia_o_attestazione_di_paternitmaternit") TypedFile stato_di_famiglia_o_attestazione_di_paternitmaternit,
                             @Part("elenco_libri_di_testo_certificato_dalla_scuola") TypedFile elenco_libri_di_testo_certificato_dalla_scuola,
                             @Part("certificato_di_nascita")TypedFile certificato_di_nascita,
                             @Part("certificato_di_invalidit_grave")TypedFile certificato_di_invalidit_grave,
                             @Part("certificato_di_nascita_o_di_adozione_del_figlio")TypedFile certificato_di_nascita_o_di_adozione_del_figlio,
                             @Part("stato_di_famiglia_o_attestazione_paternitmaternit")TypedFile stato_di_famiglia_o_attestazione_paternitmaternit,
                             @Part("attestato_di_iscrizione_a_scuola")TypedFile attestato_di_iscrizione_a_scuola,
                             @Part("stato_di_faiimglia_o_attestazione_paternitmaternit")TypedFile stato_di_faiimglia_o_attestazione_paternitmaternit,
                             @Part("elenco_di_dipendenti_per_i_quali_si_richiede_rimborso")TypedFile elenco_di_dipendenti_per_i_quali_si_richiede_rimborso,
                             @Part("elenco_allievi_in_formazione")TypedFile elenco_allievi_in_formazione,
                             @Part("verbale_accordo_sindacale")TypedFile verbale_accordo_sindacale,
                             @Part("attestazione_pagamento_dellultima_quota")TypedFile attestazione_pagamento_dellultima_quota,
                             @Part("elenco_dei_dipendenti_che_hanno_sostenuto_la_visita_medica")TypedFile elenco_dei_dipendenti_che_hanno_sostenuto_la_visita_medica,
                             @Part("elenco_dipendenti_che_hanno_sostenuto_la_visita_medica")TypedFile elenco_dipendenti_che_hanno_sostenuto_la_visita_medica,
                             @Part("frontespizio_dvr_con_evidenza_data_certa")TypedFile frontespizio_dvr_con_evidenza_data_certa,
                             @Part("file_immagine_del_banner_promozionale_o_del_logo")TypedFile file_immagine_del_banner_promozionale_o_del_logo,
                             @Part("progetto_formativo_finanziato")TypedFile progetto_formativo_finanziato,
                             @Part("delibera_di_approvazione_del_progetto")TypedFile delibera_di_approvazione_del_progetto,
                             @Part("elenco_partecipanti_al_corso_cognome_nome_e_c_f_")TypedFile elenco_partecipanti_al_corso_cognome_nome_e_c,

                             @Part("certificazione_accreditamento_dellorganismo_di_formazione")TypedFile certificazione_accreditamento_dellorganismo_di_formazione,
                             @Part("cv_formatori")TypedFile cv_formatori,
                             @Part("calendario_attivit_formativa_con_date_ora_sede_formatori_e_contenuti")TypedFile calendario_attivit_formativa_con_date_ora_sede_formatori_e_contenuti,
                             @Part("allegatiDesc")TypedString allegatiDesc,

                             @Part("GIORNIASSENZA")TypedString GIORNIASSENZA,
                             @Part("corsoTitolo")TypedString corsoTitolo,
                             @Part("corsoOre") TypedString corsoOre,
                             @Part("RIMBORSO")TypedString RIMBORSO,
                             @Part("corsoImportoAllievo") TypedString corsoImportoAllievo,             // added by Mayur for the request which needs this parametr
                             @Part("NALLIEVI")TypedString NALLIEVI,
                             @Part("ANNOISCRIZIONESCUOLA") TypedString ANNOISCRIZIONESCUOLA,           // added by Mayur for the request which needs this parametr
                             @Part("NOMESCUOLA")TypedString NOMESCUOLA,                                // added by Mayur for the request which needs this parametr
                            // @Part("codice_fiscale")TypedFile codice_fiscale,                          //// added by Sanket for the request which needs this parametr
                             Callback<JsonObject> response);

//elenco_partecipanti_al_corso_cognome_nome_e_c_f_
    //Save service request without bp file
    @Multipart
    @POST("/SaveServiceRequest")
    void saveServicesDetailsWithoutbpFile(@Part("tabella") TypedString tabella,
                                          @Part("action") TypedString action,
                                          @Part("idDIP") TypedString idDIP,
                                          @Part("campiSezD") TypedString campiSezD,//static and mandatory
                                          @Part("testiSezD") TypedString testiSezD,//static and mandatory
                                          @Part("allegati") TypedString allegati,//static and mandatory
                                          @Part("IBAN") TypedString IBAN,
                                          @Part("IDServizio") TypedString IDServizio,
                                          @Part("privacy1") TypedString privacy1,
                                          @Part("privacy2") TypedString privacy2,
                                          @Part("idBenef2") TypedString company_id,
                                          @Part("is_mobile_request") TypedString is_mobile_request,
                                          @Part("bpFile") TypedFile bpFile,
                                          @Part("stato_di_famiglia_o_certificato_di_paternitmaternit") TypedFile stato_di_famiglia_o_certificato_di_paternitmaternit,
                                          @Part("rimborso_1") TypedFile rimborso_1,
                                          @Part("rimborso_2") TypedFile rimborso_2,
                                          @Part("rimborso_3") TypedFile rimborso_3,
                                          @Part("rimborso_4") TypedFile rimborso_4,
                                          @Part("rimborso_5") TypedFile rimborso_5,
                                          @Part("rimborso_6") TypedFile rimborso_6,
                                          @Part("rimborso_7") TypedFile rimborso_7,
                                          @Part("rimborso_8") TypedFile rimborso_8,
                                          @Part("rimborso_9") TypedFile rimborso_9,
                                          @Part("rimborso_10") TypedFile rimborso10,
                                          @Part("countAz") TypedString countAz,
                                          @Part("parente") TypedString parente,
                                          @Part("tipoParente") TypedString tipoParente,
                                          @Part("cognomeParente") TypedString cognomeParente,
                                          @Part("nomeParente") TypedString nomeParente,
                                          @Part("luogoNascitaParente") TypedString luogoNascitaParente,
                                          @Part("dtNascitaParente") TypedString dtNascitaParente,
                                          @Part("certificato_medico_pediatra_o_ospedale") TypedFile certificato_medico_pediatra_o_ospedale,
                                          @Part("attestazione_assenza_da_lavoro") TypedFile attestazione_assenza_da_lavoro,
                                          @Part("cfParente") TypedString cfParente,
                                          @Part("ProvNascita") TypedString ProvNascita,
                                          @Part("lista_dipendenti") TypedFile lista_dipendenti,
                                          @Part("stato_di_famiglia") TypedFile stato_di_famiglia,
                                          @Part("ciFile") TypedFile ciFile,
                                          @Part("ciScadenza") TypedString ciScadenza,
                                          @Part("VisuraFile") TypedFile VisuraFile,
                                          @Part("visuraData") TypedString visuraData,
                                          @Part("stato_di_famiglia_o_attestazione_di_paternitmaternit") TypedFile stato_di_famiglia_o_attestazione_di_paternitmaternit,
                                          @Part("elenco_libri_di_testo_certificato_dalla_scuola") TypedFile elenco_libri_di_testo_certificato_dalla_scuola,
                                          @Part("certificato_di_nascita")TypedFile certificato_di_nascita,
                                          @Part("certificato_di_invalidit_grave")TypedFile certificato_di_invalidit_grave,
                                          @Part("certificato_di_nascita_o_di_adozione_del_figlio")TypedFile certificato_di_nascita_o_di_adozione_del_figlio,
                                          @Part("stato_di_famiglia_o_attestazione_paternitmaternit")TypedFile stato_di_famiglia_o_attestazione_paternitmaternit,
                                          @Part("attestato_di_iscrizione_a_scuola")TypedFile attestato_di_iscrizione_a_scuola,
                                          @Part("stato_di_faiimglia_o_attestazione_paternitmaternit")TypedFile stato_di_faiimglia_o_attestazione_paternitmaternit,
                                          @Part("elenco_di_dipendenti_per_i_quali_si_richiede_rimborso")TypedFile elenco_di_dipendenti_per_i_quali_si_richiede_rimborso,
                                          @Part("elenco_allievi_in_formazione")TypedFile elenco_allievi_in_formazione,
                                          @Part("verbale_accordo_sindacale")TypedFile verbale_accordo_sindacale,
                                          @Part("attestazione_pagamento_dellultima_quota")TypedFile attestazione_pagamento_dellultima_quota,
                                          @Part("elenco_dei_dipendenti_che_hanno_sostenuto_la_visita_medica")TypedFile elenco_dei_dipendenti_che_hanno_sostenuto_la_visita_medica,
                                          @Part("elenco_dipendenti_che_hanno_sostenuto_la_visita_medica")TypedFile elenco_dipendenti_che_hanno_sostenuto_la_visita_medica,
                                          @Part("frontespizio_dvr_con_evidenza_data_certa")TypedFile frontespizio_dvr_con_evidenza_data_certa,
                                          @Part("file_immagine_del_banner_promozionale_o_del_logo")TypedFile file_immagine_del_banner_promozionale_o_del_logo,
                                          @Part("progetto_formativo_finanziato")TypedFile progetto_formativo_finanziato,
                                          @Part("delibera_di_approvazione_del_progetto")TypedFile delibera_di_approvazione_del_progetto,
                                          @Part("elenco_partecipanti_al_corso_cognome_nome_e_c_f_")TypedFile elenco_partecipanti_al_corso_cognome_nome_e_c,

                                          @Part("certificazione_accreditamento_dellorganismo_di_formazione")TypedFile certificazione_accreditamento_dellorganismo_di_formazione,
                                          @Part("cv_formatori")TypedFile cv_formatori,
                                          @Part("calendario_attivit_formativa_con_date_ora_sede_formatori_e_contenuti")TypedFile calendario_attivit_formativa_con_date_ora_sede_formatori_e_contenuti,
                                          @Part("allegatiDesc")TypedString allegatiDesc,


                                          @Part("GIORNIASSENZA")TypedString GIORNIASSENZA,
                                          @Part("corsoTitolo")TypedString corsoTitolo,
                                          @Part("corsoOre") TypedString corsoOre,
                                          @Part("RIMBORSO")TypedString RIMBORSO,
                                          @Part("corsoImportoAllievo") TypedString corsoImportoAllievo,       // added by Mayur for the request which needs this parametr
                                          @Part("NALLIEVI")TypedString NALLIEVI,
                                          @Part("ANNOISCRIZIONESCUOLA") TypedString ANNOISCRIZIONESCUOLA,           // added by Mayur for the request which needs this parametr
                                          @Part("NOMESCUOLA")TypedString NOMESCUOLA,                                // added by Mayur for the request which needs this parametr
                                     //     @Part("codice_fiscale")TypedFile codice_fiscale,
                                          Callback<JsonObject> response);



    //User Selection Screen
    @Multipart
    @POST("/doLogin")
    void getUserType(@Part("email") TypedString api,
                     @Part("password") TypedString password,
                     @Part("action") TypedString action,
                     @Part("is_mobile_request") TypedString is_mobile_request,
                     @Part("tipout") TypedString user_type,
                     Callback<JsonObject> response);

    //For company data without bpfile
    @Multipart
    @POST("/SaveServiceRequest")
    void saveServicesDetailsForCompanyWithoutBpFile(@Part("tabella") TypedString tabella,
                                                    @Part("action") TypedString action,
                                                    @Part("idAZ") TypedString idDIP,
                                                    @Part("campiSezD") TypedString campiSezD,//static and mandatory
                                                    @Part("testiSezD") TypedString testiSezD,//static and mandatory
                                                    @Part("allegati") TypedString allegati,//static and mandatory
                                                    @Part("IBAN") TypedString IBAN,
                                                    @Part("IDServizio") TypedString IDServizio,
                                                    @Part("privacy1") TypedString privacy1,
                                                    @Part("privacy2") TypedString privacy2,
                                                    @Part("idBenef2") TypedString company_id,
                                                    @Part("is_mobile_request") TypedString is_mobile_request,
                                                    @Part("bpFile") TypedFile bpFile,
                                                    @Part("stato_di_famiglia_o_certificato_di_paternitmaternit") TypedFile stato_di_famiglia_o_certificato_di_paternitmaternit,
                                                    @Part("rimborso_1") TypedFile rimborso_1,
                                                    @Part("rimborso_2") TypedFile rimborso_2,
                                                    @Part("rimborso_3") TypedFile rimborso_3,
                                                    @Part("rimborso_4") TypedFile rimborso_4,
                                                    @Part("rimborso_5") TypedFile rimborso_5,
                                                    @Part("rimborso_6") TypedFile rimborso_6,
                                                    @Part("rimborso_7") TypedFile rimborso_7,
                                                    @Part("rimborso_8") TypedFile rimborso_8,
                                                    @Part("rimborso_9") TypedFile rimborso_9,
                                                    @Part("rimborso_10") TypedFile rimborso10,
                                                    @Part("countAz") TypedString countAz,
                                                    @Part("parente") TypedString parente,
                                                    @Part("tipoParente") TypedString tipoParente,
                                                    @Part("cognomeParente") TypedString cognomeParente,
                                                    @Part("nomeParente") TypedString nomeParente,
                                                    @Part("luogoNascitaParente") TypedString luogoNascitaParente,
                                                    @Part("dtNascitaParente") TypedString dtNascitaParente,
                                                    @Part("certificato_medico_pediatra_o_ospedale") TypedFile certificato_medico_pediatra_o_ospedale,
                                                    @Part("attestazione_assenza_da_lavoro") TypedFile attestazione_assenza_da_lavoro,
                                                    @Part("cfParente") TypedString cfParente,
                                                    @Part("ProvNascita") TypedString ProvNascita,
                                                    @Part("lista_dipendenti") TypedFile lista_dipendenti,
                                                    @Part("stato_di_famiglia") TypedFile stato_di_famiglia,
                                                    @Part("ciFile") TypedFile ciFile,
                                                    @Part("ciScadenza") TypedString ciScadenza,
                                                    @Part("VisuraFile") TypedFile VisuraFile,
                                                    @Part("visuraData") TypedString visuraData,
                                                    @Part("stato_di_famiglia_o_attestazione_di_paternitmaternit") TypedFile stato_di_famiglia_o_attestazione_di_paternitmaternit,
                                                    @Part("elenco_libri_di_testo_certificato_dalla_scuola") TypedFile elenco_libri_di_testo_certificato_dalla_scuola,
                                                    @Part("certificato_di_nascita")TypedFile certificato_di_nascita,
                                                    @Part("certificato_di_invalidit_grave")TypedFile certificato_di_invalidit_grave,
                                                    @Part("certificato_di_nascita_o_di_adozione_del_figlio")TypedFile certificato_di_nascita_o_di_adozione_del_figlio,
                                                    @Part("stato_di_famiglia_o_attestazione_paternitmaternit")TypedFile stato_di_famiglia_o_attestazione_paternitmaternit,
                                                    @Part("attestato_di_iscrizione_a_scuola")TypedFile attestato_di_iscrizione_a_scuola,
                                                    @Part("stato_di_faiimglia_o_attestazione_paternitmaternit")TypedFile stato_di_faiimglia_o_attestazione_paternitmaternit,
                                                    @Part("elenco_di_dipendenti_per_i_quali_si_richiede_rimborso")TypedFile elenco_di_dipendenti_per_i_quali_si_richiede_rimborso,
                                                    @Part("elenco_allievi_in_formazione")TypedFile elenco_allievi_in_formazione,
                                                    @Part("verbale_accordo_sindacale")TypedFile verbale_accordo_sindacale,
                                                    @Part("attestazione_pagamento_dellultima_quota")TypedFile attestazione_pagamento_dellultima_quota,
                                                    @Part("elenco_dei_dipendenti_che_hanno_sostenuto_la_visita_medica")TypedFile elenco_dei_dipendenti_che_hanno_sostenuto_la_visita_medica,
                                                    @Part("elenco_dipendenti_che_hanno_sostenuto_la_visita_medica")TypedFile elenco_dipendenti_che_hanno_sostenuto_la_visita_medica,
                                                    @Part("frontespizio_dvr_con_evidenza_data_certa")TypedFile frontespizio_dvr_con_evidenza_data_certa,
                                                    @Part("file_immagine_del_banner_promozionale_o_del_logo")TypedFile file_immagine_del_banner_promozionale_o_del_logo,
                                                    @Part("progetto_formativo_finanziato")TypedFile progetto_formativo_finanziato,
                                                    @Part("delibera_di_approvazione_del_progetto")TypedFile delibera_di_approvazione_del_progetto,
                                                    @Part("elenco_partecipanti_al_corso_cognome_nome_e_c_f_")TypedFile elenco_partecipanti_al_corso_cognome_nome_e_c,

                                                    @Part("certificazione_accreditamento_dellorganismo_di_formazione")TypedFile certificazione_accreditamento_dellorganismo_di_formazione,
                                                    @Part("cv_formatori")TypedFile cv_formatori,
                                                    @Part("calendario_attivit_formativa_con_date_ora_sede_formatori_e_contenuti")TypedFile calendario_attivit_formativa_con_date_ora_sede_formatori_e_contenuti,
                                                    @Part("allegatiDesc")TypedString allegatiDesc,

                                                    @Part("GIORNIASSENZA")TypedString GIORNIASSENZA,
                                                    @Part("corsoTitolo")TypedString corsoTitolo,
                                                    @Part("corsoOre") TypedString corsoOre,
                                                    @Part("RIMBORSO")TypedString RIMBORSO,
                                                    @Part("corsoImportoAllievo") TypedString corsoImportoAllievo,      // added by Mayur for the request which needs this parametr
                                                    @Part("NALLIEVI")TypedString NALLIEVI,                             // added by Mayur for the request which needs this parametr
                                                    @Part("ANNOISCRIZIONESCUOLA") TypedString ANNOISCRIZIONESCUOLA,           // added by Mayur for the request which needs this parametr
                                                    @Part("NOMESCUOLA")TypedString NOMESCUOLA,                                // added by Mayur for the request which needs this parametr
                                                    @Part("codice_fiscale")TypedFile codice_fiscale,
                                                    Callback<JsonObject> response);

    //For company data with bp file
    @Multipart
    @POST("/SaveServiceRequest")
    void saveServicesDetailsForCompanyWithBpFile(@Part("tabella") TypedString tabella,
                                                 @Part("action") TypedString action,
                                                 @Part("idAZ") TypedString idDIP,
                                                 @Part("campiSezD") TypedString campiSezD,//static and mandatory
                                                 @Part("bpAnnoMese") TypedString bpAnnoMese,
                                                 @Part("testiSezD") TypedString testiSezD,//static and mandatory
                                                 @Part("allegati") TypedString allegati,//static and mandatory
                                                 @Part("IBAN") TypedString IBAN,
                                                 @Part("IDServizio") TypedString IDServizio,
                                                 @Part("privacy1") TypedString privacy1,
                                                 @Part("privacy2") TypedString privacy2,
                                                 @Part("idBenef2") TypedString company_id,
                                                 @Part("is_mobile_request") TypedString is_mobile_request,
                                                 @Part("bpFile") TypedFile bpFile,
                                                 @Part("stato_di_famiglia_o_certificato_di_paternitmaternit") TypedFile stato_di_famiglia_o_certificato_di_paternitmaternit,
                                                 @Part("rimborso_1") TypedFile rimborso_1,
                                                 @Part("rimborso_2") TypedFile rimborso_2,
                                                 @Part("rimborso_3") TypedFile rimborso_3,
                                                 @Part("rimborso_4") TypedFile rimborso_4,
                                                 @Part("rimborso_5") TypedFile rimborso_5,
                                                 @Part("rimborso_6") TypedFile rimborso_6,
                                                 @Part("rimborso_7") TypedFile rimborso_7,
                                                 @Part("rimborso_8") TypedFile rimborso_8,
                                                 @Part("rimborso_9") TypedFile rimborso_9,
                                                 @Part("rimborso_10") TypedFile rimborso10,
                                                 @Part("countAz") TypedString countAz,
                                                 @Part("parente") TypedString parente,
                                                 @Part("tipoParente") TypedString tipoParente,
                                                 @Part("cognomeParente") TypedString cognomeParente,
                                                 @Part("nomeParente") TypedString nomeParente,
                                                 @Part("luogoNascitaParente") TypedString luogoNascitaParente,
                                                 @Part("dtNascitaParente") TypedString dtNascitaParente,
                                                 @Part("certificato_medico_pediatra_o_ospedale") TypedFile certificato_medico_pediatra_o_ospedale,
                                                 @Part("attestazione_assenza_da_lavoro") TypedFile attestazione_assenza_da_lavoro,
                                                 @Part("cfParente") TypedString cfParente,
                                                 @Part("ProvNascita") TypedString ProvNascita,
                                                 @Part("lista_dipendenti") TypedFile lista_dipendenti,
                                                 @Part("stato_di_famiglia") TypedFile stato_di_famiglia,
                                                 @Part("ciFile") TypedFile ciFile,
                                                 @Part("ciScadenza") TypedString ciScadenza,
                                                 @Part("VisuraFile") TypedFile VisuraFile,
                                                 @Part("visuraData") TypedString visuraData,
                                                 @Part("stato_di_famiglia_o_attestazione_di_paternitmaternit") TypedFile stato_di_famiglia_o_attestazione_di_paternitmaternit,
                                                 @Part("elenco_libri_di_testo_certificato_dalla_scuola") TypedFile elenco_libri_di_testo_certificato_dalla_scuola,
                                                 @Part("certificato_di_nascita")TypedFile certificato_di_nascita,
                                                 @Part("certificato_di_invalidit_grave")TypedFile certificato_di_invalidit_grave,
                                                 @Part("certificato_di_nascita_o_di_adozione_del_figlio")TypedFile certificato_di_nascita_o_di_adozione_del_figlio,
                                                 @Part("stato_di_famiglia_o_attestazione_paternitmaternit")TypedFile stato_di_famiglia_o_attestazione_paternitmaternit,
                                                 @Part("attestato_di_iscrizione_a_scuola")TypedFile attestato_di_iscrizione_a_scuola,
                                                 @Part("stato_di_faiimglia_o_attestazione_paternitmaternit")TypedFile stato_di_faiimglia_o_attestazione_paternitmaternit,
                                                 @Part("elenco_di_dipendenti_per_i_quali_si_richiede_rimborso")TypedFile elenco_di_dipendenti_per_i_quali_si_richiede_rimborso,
                                                 @Part("elenco_allievi_in_formazione")TypedFile elenco_allievi_in_formazione,
                                                 @Part("verbale_accordo_sindacale")TypedFile verbale_accordo_sindacale,
                                                 @Part("attestazione_pagamento_dellultima_quota")TypedFile attestazione_pagamento_dellultima_quota,
                                                 @Part("elenco_dei_dipendenti_che_hanno_sostenuto_la_visita_medica")TypedFile elenco_dei_dipendenti_che_hanno_sostenuto_la_visita_medica,
                                                 @Part("elenco_dipendenti_che_hanno_sostenuto_la_visita_medica")TypedFile elenco_dipendenti_che_hanno_sostenuto_la_visita_medica,
                                                 @Part("frontespizio_dvr_con_evidenza_data_certa")TypedFile frontespizio_dvr_con_evidenza_data_certa,
                                                 @Part("file_immagine_del_banner_promozionale_o_del_logo")TypedFile file_immagine_del_banner_promozionale_o_del_logo,
                                                 @Part("progetto_formativo_finanziato")TypedFile progetto_formativo_finanziato,
                                                 @Part("delibera_di_approvazione_del_progetto")TypedFile delibera_di_approvazione_del_progetto,
                                                 @Part("elenco_partecipanti_al_corso_cognome_nome_e_c_f_")TypedFile elenco_partecipanti_al_corso_cognome_nome_e_c,

                                                 @Part("certificazione_accreditamento_dellorganismo_di_formazione")TypedFile certificazione_accreditamento_dellorganismo_di_formazione,
                                                 @Part("cv_formatori")TypedFile cv_formatori,
                                                 @Part("calendario_attivit_formativa_con_date_ora_sede_formatori_e_contenuti")TypedFile calendario_attivit_formativa_con_date_ora_sede_formatori_e_contenuti,
                                                 @Part("allegatiDesc")TypedString allegatiDesc,

                                                 @Part("GIORNIASSENZA")TypedString GIORNIASSENZA,
                                                 @Part("corsoTitolo")TypedString corsoTitolo,
                                                 @Part("corsoOre") TypedString corsoOre,
                                                 @Part("RIMBORSO")TypedString RIMBORSO,
                                                 @Part("corsoImportoAllievo") TypedString corsoImportoAllievo,      // added by Mayur for the request which needs this parametr
                                                 @Part("NALLIEVI")TypedString NALLIEVI,
                                                 @Part("ANNOISCRIZIONESCUOLA") TypedString ANNOISCRIZIONESCUOLA,           // added by Mayur for the request which needs this parametr
                                                 @Part("NOMESCUOLA")TypedString NOMESCUOLA,                                // added by Mayur for the request which needs this parametr
                                                 @Part("codice_fiscale")TypedFile codice_fiscale,                                // added by Sanket for the request which needs this parametr
                                                 Callback<JsonObject> response);
/*certificazione_accreditamento_dellorganismo_di_formazione,
cv_formatori,
calendario_attivit_formativa_con_date_ora_sede_formatori_e_contenuti
allegatiDesc*/

    //Service details
    /*@Multipart
    @POST("/changePassword")
    void changePassword(@Part("action") TypedString action,
                            @Part("tabella") TypedString tabella,
                            @Part("existing_password") TypedString existing_password,
                            @Part("new_password") TypedString new_password,
                            @Part("confirm_password") TypedString confirm_password,
                            Callback<JsonObject> response);*/

    @GET("/index_production.php")
    void changePassword(@Query("action") String action,
                        @Query("tabella") String tabella,
                        @Query("existing_password") String existing_password,
                        @Query("new_password") String new_password,
                        @Query("confirm_password") String confirm_password,
                        @Query("user_id") String user_id,
                        Callback<JsonObject> response);

    //@Query("action") String action,

    @GET("/getUserServices")
    void requestService(@Query("action") String action,
                          @Query("tabella") String tabella,
                        @Query("user_id") String user_id,
                        Callback<JsonObject> response);


    @GET("/getVersionList")
   // @GET("/index_new.php")
    void getVersameti(@Query("action") String action,
                        @Query("tabella") String tabella,
                        @Query("user_id") String user_id,
                        Callback<JsonObject> response);

    @GET("/updateVersionList")
    void editVersameti(@Query("action") String action,
                      @Query("tabella") String tabella,
                      @Query("user_id") String user_id,
                       @Query("AzNome") String AzNome,
                       @Query("AnnoComp") String AnnoComp,
                       @Query("cf") String cf,
                       @Query("piva") String piva,
                       @Query("ebv_ver") String ebv_ver,
                       @Query("INPS") String INPS,
                       @Query("GEN") String GEN,
                       @Query("FEB") String FEB,
                       @Query("MAR") String MAR,
                       @Query("APR") String APR,
                       @Query("MAG") String MAG,
                       @Query("GIU") String GIU,
                       @Query("LUG") String LUG,
                       @Query("AGO") String AGO,
                       @Query("SET") String SET,
                       @Query("OTT") String OTT,
                       @Query("NOV") String NOV,
                       @Query("DIC") String DIC,
                       Callback<JsonObject> response);

    @GET("/getSeatList")
    void getSedi(@Query("action") String action,
                      @Query("tabella") String tabella,
                      @Query("user_id") String user_id,
                      Callback<JsonObject> response);

   /* http://www.ebveneto.it/web_services/index_new.php?action=updateSeatList&tabella=Aziende&user_id=00097A&idSede=&
    // Nome=&Via=&Cap=&Comune=&Frazione=&Prov=&Tipo=&fonte=*/

    @GET("/updateSeatList")
    void editSedi(@Query("action") String action,
                       @Query("tabella") String tabella,
                       @Query("user_id") String user_id,
                       @Query("idSede") String idSede,
                       @Query("Nome") String Nome,
                       @Query("Via") String Via,
                       @Query("Cap") String Cap,
                       @Query("Comune") String Comune,
                       @Query("Frazione") String Frazione,
                       @Query("Prov") String Prov,
                       @Query("Tipo") String Tipo,
                       @Query("fonte") String fonte,
                  Callback<JsonObject> response);


    @Multipart
    @POST("/updateUserConsent")
    void updateUserConsent(@Part("tabella") TypedString tabella,
                           @Part("id") TypedString user_id,
                           @Part("action") TypedString action,
                           Callback<JsonObject> response);


    //Save service request
    @Multipart
    @POST("/SaveServiceRequest")
    void saveServicesDetails(@Part("tabella") TypedString tabella,
                             @Part("action") TypedString action,
                             @PartMap Map<String, TypedString> userId,
                             @Part("campiSezD") TypedString campiSezD,//static and mandatory
                           //  @Part("bpAnnoMese") TypedString bpAnnoMese,
                             @Part("testiSezD") TypedString testiSezD,//static and mandatory
                             @Part("allegati") TypedString allegati,//static and mandatory
                             @Part("IBAN") TypedString IBAN,
                             @Part("IDServizio") TypedString IDServizio,
                             @Part("privacy1") TypedString privacy1,
                             @Part("privacy2") TypedString privacy2,
                             @Part("idBenef2") TypedString company_id,
                             @Part("is_mobile_request") TypedString is_mobile_request,
                             @Part("countAz") TypedString countAz,
                             @Part("parente") TypedString parente,
                             @Part("tipoParente") TypedString tipoParente,
                             @Part("cognomeParente") TypedString cognomeParente,
                             @Part("nomeParente") TypedString nomeParente,
                             @Part("luogoNascitaParente") TypedString luogoNascitaParente,
                             @Part("dtNascitaParente") TypedString dtNascitaParente,
                             @Part("cfParente") TypedString cfParente,
                             @Part("ProvNascita") TypedString ProvNascita,
                             @Part("ciScadenza") TypedString ciScadenza,
                             @Part("visuraData") TypedString visuraData,
                             @Part("allegatiDesc")TypedString allegatiDesc,
                             @Part("GIORNIASSENZA")TypedString GIORNIASSENZA,
                             @Part("corsoTitolo")TypedString corsoTitolo,
                             @Part("corsoOre") TypedString corsoOre,
                             @Part("RIMBORSO")TypedString RIMBORSO,
                             @Part("corsoImportoAllievo") TypedString corsoImportoAllievo,             // added by Mayur for the request which needs this parametr
                             @Part("NALLIEVI")TypedString NALLIEVI,
                             @Part("ANNOISCRIZIONESCUOLA") TypedString ANNOISCRIZIONESCUOLA,           // added by Mayur for the request which needs this parametr
                             @Part("NOMESCUOLA")TypedString NOMESCUOLA,                                // added by Mayur for the request which needs this parametr
                             @PartMap Map<String, TypedFile> dynamicFiles,
                             Callback<JsonObject> response);

    @GET("/index_production_march19.php?action=getAppVersion&type=android")
    void updateAvailable(Callback<JsonObject> response);

    @Multipart
    @POST("/uploadAttach")
    void uploadAttachment(
            @Part("user_id") TypedString user_id,
            @Part("ids") TypedString ids,
            @PartMap Map<String, TypedFile> dynamicFiles,
            @Part("action") TypedString action,
            Callback<JsonObject> response);

}
