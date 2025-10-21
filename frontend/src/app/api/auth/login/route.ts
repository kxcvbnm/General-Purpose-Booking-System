import { NextResponse } from "next/server";

export async function POST(req: Request) {
    const body = await req.json();
    const res = await fetch(`${process.env.NEXT_PUBLIC_API_BASE}/api/v1/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
    });

    if(!res.ok){
        const error = await res.text();
        return NextResponse.json({ ok: false, error }, { status: res.status });
    }

    const tokens = await res.json();
    const next = NextResponse.json({
        ok: true
    });

    // HttpOnly cookies
    next.cookies.set(process.env.ACCESS_COOKIE!, tokens.accessToken, {
        httpOnly: true,
        secure: true,
        sameSite: "lax",
        path: "/",
        maxAge: 60 * 10,  // 10 minutes
    });
    next.cookies.set(process.env.REFRESH_COOKIE!, tokens.refreshToken, {
        httpOnly: true,
        secure: true,
        sameSite: "lax",
        path: "/",
        maxAge: 60 * 60 * 24 * 7, // 7 days
    });

    return next;
}