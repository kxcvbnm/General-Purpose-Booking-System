import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
    const refresh = request.cookies.get('refreshToken')?.value;
    if(!refresh) return NextResponse.json({ error: 'Refresh token not found' }, { status: 401 });

    const res = await fetch(`${process.env.BASE_API}/api/v1/auth/refresh`, {
        method: "POST",
        headers: { "Content-type": "application/json" },
        body: JSON.stringify({ refreshToken: refresh }),
    });

    if(!res.ok) {
        const message = await res.text();
        return NextResponse.json(message, { status: res.status });
    }

    const { accessToken, refreshToken } = await res.json();

    const response = NextResponse.json(null, { status: 204 });
    if(accessToken) {
        response.cookies.set("accessToken", accessToken, {
            httpOnly: true,
            secure: true,
            sameSite: "strict",
            path: "/",
            maxAge: 60 * 10,     // 10 minutes
        });
    }
    if(refreshToken) {
        response.cookies.set("refreshToken", refreshToken, {
            httpOnly: true,
            secure: true,
            sameSite: "strict",
            path: "/api/auth",
            maxAge: 60 * 60 * 24 * 7,     // 7 days
        });
    }

    return response;
}