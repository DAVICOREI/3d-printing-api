package com.daviribeiro.ecommerce3d.services;

import com.daviribeiro.ecommerce3d.entities.Pedido;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    // Puxa o seu novo token gerado lá do application.properties
    @Value("${mercadopago.access.token}")
    private String accessToken;

    // Coloque aqui o link real que a Vercel gerou para você
    private final String frontendUrl = "https://ecommerce3d-daviribeiro.vercel.app"; // <-- Lembre de ajustar para a sua URL da Vercel

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public String criarPreferencia(Pedido pedido) {
        try {
            // 1. Cria o item da compra pegando o ID real do pedido e o valor total exato calculado no carrinho
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title("Encomenda 3D - Pedido #" + pedido.getId())
                    .quantity(1)
                    .unitPrice(pedido.getTotal()) // Pega a soma real do banco (BigDecimal)
                    .currencyId("BRL")
                    .build();

            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            // 2. Configura as URLs de retorno (quando o cliente paga ou cancela)
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success(frontendUrl + "/pedidos") // Sucesso: joga o cliente para a tela de 'Meus Pedidos'
                    .failure(frontendUrl + "/")        // Falha: volta para a vitrine
                    .pending(frontendUrl + "/pedidos")
                    .build();

            // 3. Monta a preferência completa
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .build();

            // 4. Faz a chamada para a API do Mercado Pago
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            // Retorna o link de pagamento criptografado
            return preference.getInitPoint();

        } catch (Exception e) {
            System.out.println("Erro ao gerar link do Mercado Pago: " + e.getMessage());
            return null;
        }
    }
}