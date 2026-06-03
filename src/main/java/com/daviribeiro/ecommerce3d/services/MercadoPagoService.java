package com.daviribeiro.ecommerce3d.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    // Coloque aqui o link real que a Vercel gerou para você
    private final String FRONTEND_URL = "https://3d-printing-ecommerce.vercel.app";

    @PostConstruct
    public void init() {
        // Inicializa o SDK com o seu Token
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public String criarPreferencia() {
        try {
            // 1. O que está sendo comprado (Aqui depois vamos receber os itens do carrinho)
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title("Pedido Loja 3D")
                    .quantity(1)
                    .unitPrice(new BigDecimal("150.00")) // Valor de teste
                    .currencyId("BRL")
                    .build();

            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            // 2. AQUI ESTÃO OS SEUS BACK URLS (Exatamente como na sua imagem)
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success(FRONTEND_URL + "/sucesso")
                    .pending(FRONTEND_URL + "/pendente")
                    .failure(FRONTEND_URL + "/erro")
                    .build();

            // 3. Montar a Preferência completa
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .autoReturn("approved") // Volta automaticamente para o seu site se aprovar!
                    .build();

            // 4. Enviar para o Mercado Pago e receber a resposta
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            // 5. Retorna o link de pagamento (init_point)
            return preference.getInitPoint();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}