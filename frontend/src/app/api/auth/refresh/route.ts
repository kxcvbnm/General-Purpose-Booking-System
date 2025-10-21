import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function POST() {
    const refresh = (await cookies()).get(process.env.REFRESH_COOKIE!);

    if(!refresh)
        return NextResponse.json({
            ok: false
        }, {
            status: 401
        });

    const res = await fetch(`${process.env.NEXT_PUBLIC_API_BASE}/api/v1/auth/refresh`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ refreshToken: refresh.value }),
    });

    if(!res.ok){
        const error = await res.text();
        return NextResponse.json({ ok: false, error }, { status: res.status });
    }

    const tokens = await res.json();

    const next = NextResponse.json({    
        ok: true
    });
    next.cookies.set(process.env.ACCESS_COOKIE!, tokens.accessToken, {
        httpOnly: true,
        secure: true,
        sameSite: "lax",
        path: "/",
        maxAge: 60 * 10,  // 10 minutes
    });

    return next;
}