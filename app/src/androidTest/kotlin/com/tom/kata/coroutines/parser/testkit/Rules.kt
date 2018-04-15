package com.tom.kata.coroutines.parser.testkit

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import okreplay.*
import org.junit.rules.RuleChain
import org.junit.rules.TestRule


/**
 * Sets up hermetic environment.
 */
inline fun <reified TargetActivity : Activity> setupEnvAndLaunchActivity(): TestRule =
    setupEnv<TargetActivity>(ActivityTestRule<TargetActivity>(TargetActivity::class.java))

/**
 * Creates a chain of rules that setup hermetic test environment, grants permission. We rely on [OkReplay] to
 * isolate the network requests.
 *
 * We do rely on [MatchRules.uri] policy to create tapes. The tape mode rule should be overridden by use of:
 *
 * ```
 * @OkReplay(mode = TapeMode.READ_WRITE)
 * ```
 */
inline fun <reified TargetActivity : Activity> setupEnv(activityTestRule: TestRule): TestRule {
    val component = AppUnderTest.component()
    val okReplayInterceptor = component.okHttpClient().interceptors()
        .find { it is OkReplayInterceptor } as OkReplayInterceptor

    val configuration = OkReplayConfig.Builder()
        .tapeRoot(AndroidTapeRoot(InstrumentationRegistry.getContext(), TargetActivity::class.java))
        .sslEnabled(true)
        .defaultMatchRule(MatchRules.uri)
        .interceptor(okReplayInterceptor)
        .build()

    val permissionsRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    return RuleChain.outerRule(permissionsRule)
        .around(PermissionRule(configuration))
        .around(CoroutinesIdlingResourceRule())
        .around(RecorderRule(configuration))
        .around(activityTestRule)
}


