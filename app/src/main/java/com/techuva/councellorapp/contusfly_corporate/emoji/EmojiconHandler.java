/*
 * Copyright 2014 Ankush Sachdeva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.techuva.councellorapp.contusfly_corporate.emoji;

import android.content.Context;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.util.SparseIntArray;

import com.techuva.councellorapp.R;


/**
 * The Class EmojiconHandler.
 *
 * @author Hieu Rocker (rockerhieu@gmail.com)
 */
public final class EmojiconHandler {

    /**
     * Instantiates a new emojicon handler.
     */
    private EmojiconHandler() {
    }

    /**
     * The Constant mEmojiMap.
     */
    private static final SparseIntArray mEmojiMap = new SparseIntArray(846);

    /**
     * The Constant mBankMap.
     */
    private static final SparseIntArray mBankMap = new SparseIntArray(471);

    static {
        // People
        mEmojiMap.put(0x1f600, R.drawable.emoji_1f600);
        mEmojiMap.put(0x1f601, R.drawable.emoji_1f601);
        mEmojiMap.put(0x1f602, R.drawable.emoji_1f602);
        mEmojiMap.put(0x1f603, R.drawable.emoji_1f603);
        mEmojiMap.put(0x1f604, R.drawable.emoji_1f604);
        mEmojiMap.put(0x1f605, R.drawable.emoji_1f605);
        mBankMap.put(0xe404, R.drawable.emoji_1f601);
        mBankMap.put(0xe057, R.drawable.emoji_1f603);
        mBankMap.put(0xe412, R.drawable.emoji_1f602);
        mBankMap.put(0xe415, R.drawable.emoji_1f605);
    }
    /**
     * Checks if is soft bank emoji.
     *
     * @param c the c
     * @return true, if is soft bank emoji
     */
    private static boolean isSoftBankEmoji(char c) {
        return ((c >> 12) == 0xe);
    }

    /**
     * Gets the emoji resource.
     *
     * @param context   the context
     * @param codePoint the code point
     * @return the emoji resource
     */
    private static int getEmojiResource(Context context, int codePoint) {
        return mEmojiMap.get(codePoint);
    }

    /**
     * Gets the softbank emoji resource.
     *
     * @param c the c
     * @return the softbank emoji resource
     */
    private static int getSoftbankEmojiResource(char c) {
        return mBankMap.get(c);
    }

    public static int getSpannableLength(Spannable text) {
        int textLength = text.length();
        // remove spans throughout all text
        EmojiconSpan[] oldSpans = text.getSpans(0, textLength, EmojiconSpan.class);
        ImageSpan[] imageSpans = text.getSpans(0, textLength, ImageSpan.class);
        return oldSpans.length + imageSpans.length;
    }

    public static int getLength(Spannable text) {
        int textLength = text.length();
        return textLength - getSpannableLength(text);
    }

    /**
     * Convert emoji characters of the given Spannable to the according
     * emojicon.
     *
     * @param context   the context
     * @param text      the text
     * @param emojiSize the emoji size
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize) {
        addEmojis(context, text, emojiSize, 0, -1);
    }

    /**
     * Convert emoji characters of the given Spannable to the according
     * emojicon.
     *
     * @param context   the context
     * @param text      the text
     * @param emojiSize the emoji size
     * @param index     the index
     * @param length    the length
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int index, int length) {
        int textLength = text.length();
        int textLengthToProcessMax = textLength - index;
        int textLengthToProcess = length < 0 || length >= textLengthToProcessMax ? textLength : (length + index);

        // remove spans throughout all text
        EmojiconSpan[] oldSpans = text.getSpans(0, textLength, EmojiconSpan.class);
        for (int i = 0; i < oldSpans.length; i++) {
            text.removeSpan(oldSpans[i]);
        }
        int skip;
        for (int i = index; i < textLengthToProcess; i += skip) {
            skip = 0;
            int icon = 0;
            char c = text.charAt(i);
            if (isSoftBankEmoji(c)) {
                icon = getSoftbankEmojiResource(c);
                skip = icon == 0 ? 0 : 1;
            }

            if (icon == 0) {
                int unicode = Character.codePointAt(text, i);
                skip = Character.charCount(unicode);

                if (unicode > 0xff) {
                    icon = getEmojiResource(context, unicode);
                }

                if (icon == 0 && i + skip < textLengthToProcess) {
                    int followUnicode = Character.codePointAt(text, i + skip);
                    if (followUnicode == 0x20e3) {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            case 0x0031:
                                icon = R.drawable.emoji_1f600;
                                break;
                            default:
                                followSkip = 0;
                                break;
                        }
                        skip += followSkip;
                    } else {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            case 0x1f1ef:
                                icon = (followUnicode == 0x1f1f5) ? R.drawable.emoji_1f600 : 0;
                                break;
                                 default:
                                followSkip = 0;
                                break;
                        }
                        skip += followSkip;
                    }
                }
            }

            if (icon > 0) {
                text.setSpan(new EmojiconSpan(context, icon, emojiSize), i, i + skip,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}
