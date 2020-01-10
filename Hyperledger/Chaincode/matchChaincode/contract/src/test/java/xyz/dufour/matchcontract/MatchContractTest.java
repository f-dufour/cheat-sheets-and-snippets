package xyz.dufour.matchcontract;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;

import lombok.SneakyThrows;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.dufour.matchcontract.model.Team;


public final class MatchContractTest {

    @Nested
    class AssetExists {
        @Test
        public void noProperAsset() {

            MatchContract contract = new  MatchContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState(MatchAsset.DOCTYPE + ".1")).thenReturn(new byte[] {});
            boolean result = contract.matchExists(ctx,"1");

            assertFalse(result);
        }

        @Test
        public void assetExists() {

            MatchContract contract = new  MatchContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);

            when(ctx.getStub()).thenReturn(stub);
            when(stub.getState(MatchAsset.DOCTYPE +".42")).thenReturn(new byte[] {42});

            boolean result = contract.matchExists(ctx,"42");

            assertTrue(result);

        }

        @Test
        public void noKey() {
            MatchContract contract = new  MatchContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState(MatchAsset.DOCTYPE + ".1")).thenReturn(null);
            boolean result = contract.matchExists(ctx,"1");

            assertFalse(result);

        }

    }

    @Nested
    class AssetCreates {

        @SneakyThrows
        @Test
        public void newAssetCreate() {
            MatchContract contract = new  MatchContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);

            when(ctx.getStub()).thenReturn(stub);

            String jsonString = new MatchAsset("1", Team.PSG, Team.ASNL, "20200110").toJSONString();

            contract.createMatch(ctx,
                    "1",
                    Team.PSG,
                    Team.ASNL,
                    "20200110");

            verify(stub).putState(MatchAsset.DOCTYPE + ".1", jsonString.getBytes(UTF_8));
        }

        @Test
        public void alreadyExists() {
            MatchContract contract = new  MatchContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);

            when(ctx.getStub()).thenReturn(stub);
            when(stub.getState(MatchAsset.DOCTYPE + ".1")).thenReturn(new byte[] { 42 });

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.createMatch(ctx, "1", Team.PSG, Team.ASNL, "20200110");
            });

            assertEquals(thrown.getMessage(), "The match 1 already exists");

        }

    }

    @SneakyThrows
    @Test
    public void assetRead() {
        MatchContract contract = new  MatchContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);

        when(ctx.getStub()).thenReturn(stub);

        MatchAsset asset = new MatchAsset("1", Team.PSG, Team.ASNL, "20200110");
        String jsonString = asset.toJSONString();

        when(stub.getState(MatchAsset.DOCTYPE + ".1")).thenReturn(jsonString.getBytes(StandardCharsets.UTF_8));
        MatchAsset returnedAsset = contract.readMatch(ctx, "1");

        assertEquals(returnedAsset.toJSONString(), asset.toJSONString());
    }

    @Nested
    class AssetUpdates {
        @SneakyThrows
        @Test
        public void updateExisting() {
            MatchContract contract = new  MatchContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getState(MatchAsset.DOCTYPE + ".1")).thenReturn(new byte[] { 42 });

            contract.updateMatch(ctx, "1", Team.OM, Team.RCS, "20200110");
            String jsonString = new MatchAsset("1", Team.OM, Team.RCS, "20200110").toJSONString();

            verify(stub).putState(MatchAsset.DOCTYPE + ".1", jsonString.getBytes(UTF_8));
        }

        @Test
        public void updateMissing() {
            MatchContract contract = new  MatchContract();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState(MatchAsset.DOCTYPE + ".1")).thenReturn(null);

            Exception thrown = assertThrows(RuntimeException.class, () -> {
                contract.updateMatch(ctx, "1", Team.RCS, Team.ASNL, "20200110");
            });

            assertEquals(thrown.getMessage(), "The match 1 does not exist");
        }

    }

    @Test
    public void assetDelete() {
        MatchContract contract = new  MatchContract();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);
        when(stub.getState("1")).thenReturn(null);

        Exception thrown = assertThrows(RuntimeException.class, () -> {
            contract.deleteMatch(ctx, "1");
        });

        assertEquals(thrown.getMessage(), "The match 1 does not exist");
    }

}
