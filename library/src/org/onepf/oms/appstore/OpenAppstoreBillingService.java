/*******************************************************************************
 * Copyright 2013 One Platform Foundation
 *
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 ******************************************************************************/

package org.onepf.oms.appstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.android.vending.billing.IInAppBillingService;
import org.onepf.oms.AppstoreInAppBillingService;
import org.onepf.oms.appstore.googleUtils.IabException;
import org.onepf.oms.appstore.googleUtils.IabHelper;
import org.onepf.oms.appstore.googleUtils.Inventory;
import org.onepf.oms.appstore.googleUtils.Purchase;

import java.util.List;

/**
 * User: Boris Minaev
 * Date: 28.05.13
 * Time: 3:03
 */
public class OpenAppstoreBillingService implements AppstoreInAppBillingService{
    IInAppBillingService mIInAppBillingService;
    IabHelperBillingService mIabHelperBillingService;
    String mPublicKey;
    IabHelper mIabHelper;

    public OpenAppstoreBillingService(IInAppBillingService iOpenInAppBillingService, String publicKey, Context context) {
        mIInAppBillingService = iOpenInAppBillingService;
        mPublicKey = publicKey;
        mIabHelper = new IabHelper(context, publicKey);
        mIabHelperBillingService = new IabHelperBillingService(mIInAppBillingService, context);
    }

    @Override
    public void startSetup(IabHelper.OnIabSetupFinishedListener listener, IabHelperBillingService billingService) {
        mIabHelper.startSetup(listener, mIabHelperBillingService);
    }

    @Override
    public void launchPurchaseFlow(Activity act, String sku, String itemType, int requestCode, IabHelper.OnIabPurchaseFinishedListener listener, String extraData) {
        mIabHelper.launchPurchaseFlow(act, sku, itemType, requestCode, listener, extraData);
    }

    @Override
    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        return mIabHelper.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Inventory queryInventory(boolean querySkuDetails, List<String> moreItemSkus, List<String> moreSubsSkus) throws IabException {
        return mIabHelper.queryInventory(querySkuDetails, moreItemSkus, moreSubsSkus);
    }

    @Override
    public void consume(Purchase itemInfo) throws IabException {
        mIabHelper.consume(itemInfo);
    }

    @Override
    public void dispose() {
        mIabHelper.dispose();
    }
}
